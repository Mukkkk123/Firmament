package moe.nea.notfirmament.features.debug.itemeditor

import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.relativeTo
import kotlin.io.path.writeText
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.nbt.StringTag
import net.minecraft.network.chat.Component
import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.commands.RestArgumentType
import moe.nea.notfirmament.commands.get
import moe.nea.notfirmament.commands.thenArgument
import moe.nea.notfirmament.commands.thenExecute
import moe.nea.notfirmament.commands.thenLiteral
import moe.nea.notfirmament.events.CommandEvent
import moe.nea.notfirmament.events.HandledScreenKeyPressedEvent
import moe.nea.notfirmament.events.SlotRenderEvents
import moe.nea.notfirmament.features.debug.DeveloperFeatures
import moe.nea.notfirmament.features.debug.ExportedTestConstantMeta
import moe.nea.notfirmament.features.debug.PowerUserTools
import moe.nea.notfirmament.repo.RepoDownloadManager
import moe.nea.notfirmament.repo.RepoManager
import moe.nea.notfirmament.util.LegacyTagParser
import moe.nea.notfirmament.util.LegacyTagWriter.Companion.toLegacyString
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.SkyblockId
import moe.nea.notfirmament.util.focusedItemStack
import moe.nea.notfirmament.util.mc.SNbtFormatter.Companion.toPrettyString
import moe.nea.notfirmament.util.mc.displayNameAccordingToNbt
import moe.nea.notfirmament.util.mc.loreAccordingToNbt
import moe.nea.notfirmament.util.mc.toNbtList
import moe.nea.notfirmament.util.render.drawGuiTexture
import moe.nea.notfirmament.util.setSkyBlockId
import moe.nea.notfirmament.util.skyBlockId
import moe.nea.notfirmament.util.tr

object ItemExporter {

	fun exportItem(itemStack: ItemStack): Component {
		nonOverlayCache.clear()
		val exporter = LegacyItemExporter.createExporter(itemStack)
		var json = exporter.exportJson()
		val fileName = json.jsonObject["internalname"]?.jsonPrimitive?.takeIf { it.isString }?.content
		if (fileName == null) {
			return tr(
				"notfirmament.repoexport.nointernalname",
				"Could not find internal name to export for this item (null.json)"
			)
		}
		val itemFile = RepoDownloadManager.repoSavedLocation.resolve("items").resolve("${fileName}.json")
		itemFile.createParentDirectories()
		if (itemFile.exists()) {
			val existing = try {
				NotFirmament.json.decodeFromString<JsonObject>(itemFile.readText())
			} catch (ex: Exception) {
				ex.printStackTrace()
				JsonObject(mapOf())
			}
			val mut = json.jsonObject.toMutableMap()
			for (prop in existing) {
				if (prop.key !in mut || mut[prop.key]!!.let {
						(it is JsonPrimitive && (it.content.isEmpty() || it.content == "0")) || (it is JsonArray && it.isEmpty()) || (it is JsonObject && it.isEmpty())
					})
					mut[prop.key] = prop.value
			}
			json = JsonObject(mut)
		}
		val jsonFormatted = NotFirmament.twoSpaceJson.encodeToString(json)
		itemFile.writeText(jsonFormatted)
		val overlayFile = RepoDownloadManager.repoSavedLocation.resolve("itemsOverlay")
			.resolve(ExportedTestConstantMeta.current.dataVersion.toString())
			.resolve("${fileName}.snbt")
		overlayFile.createParentDirectories()
		overlayFile.writeText(exporter.exportModernSnbt().toPrettyString())
		return tr(
			"notfirmament.repoexport.success",
			"Exported item to ${itemFile.relativeTo(RepoDownloadManager.repoSavedLocation)}${
				exporter.warnings.joinToString(
					""
				) { "\nWarning: $it" }
			}"
		)
	}

	fun pathFor(skyBlockId: SkyblockId) =
		RepoManager.neuRepo.baseFolder.resolve("items/${skyBlockId.neuItem}.json")

	fun isExported(skyblockId: SkyblockId) =
		pathFor(skyblockId).exists()

	fun ensureExported(itemStack: ItemStack) {
		if (!isExported(itemStack.skyBlockId ?: return))
			MC.sendChat(exportItem(itemStack))
	}

	fun modifyJson(skyblockId: SkyblockId, modify: (JsonObject) -> JsonObject) {
		val oldJson = NotFirmament.json.decodeFromString<JsonObject>(pathFor(skyblockId).readText())
		val newJson = modify(oldJson)
		pathFor(skyblockId).writeText(NotFirmament.twoSpaceJson.encodeToString(JsonObject(newJson)))
	}

	fun appendRecipe(skyblockId: SkyblockId, recipe: JsonObject) {
		modifyJson(skyblockId) { oldJson ->
			val mutableJson = oldJson.toMutableMap()
			val recipes = ((mutableJson["recipes"] as JsonArray?) ?: listOf()).toMutableList()
			recipes.add(recipe)
			mutableJson["recipes"] = JsonArray(recipes)
			JsonObject(mutableJson)
		}
	}

	@Subscribe
	fun onCommand(event: CommandEvent.SubCommand) {
		event.subcommand(DeveloperFeatures.DEVELOPER_SUBCOMMAND) {
			thenLiteral("reexportlore") {
				thenArgument("itemid", RestArgumentType) { itemid ->
					suggests { ctx, builder ->
						val spaceIndex = builder.remaining.lastIndexOf(" ")
						val (before, after) =
							if (spaceIndex < 0) Pair("", builder.remaining)
							else Pair(
								builder.remaining.substring(0, spaceIndex + 1),
								builder.remaining.substring(spaceIndex + 1)
							)
						RepoManager.neuRepo.items.items.keys
							.asSequence()
							.filter { it.startsWith(after, ignoreCase = true) }
							.forEach {
								builder.suggest(before + it)
							}

						builder.buildFuture()
					}
					thenExecute {
						for (itemid in get(itemid).split(" ").map { SkyblockId(it) }) {
							if (pathFor(itemid).notExists()) {
								MC.sendChat(
									tr(
										"notfirmament.repo.export.relore.fail",
										"Could not find json file to relore for ${itemid}"
									)
								)
							}
							fixLoreNbtFor(itemid)
							MC.sendChat(
								tr(
									"notfirmament.repo.export.relore",
									"Updated lore / display name for $itemid"
								)
							)
						}
					}
				}
				thenLiteral("all") {
					thenExecute {
						var i = 0
						val chunkSize = 100
						val items = RepoManager.neuRepo.items.items.keys
						NotFirmament.coroutineScope.launch {
							items.chunked(chunkSize).forEach { key ->
								MC.sendChat(
									tr(
										"notfirmament.repo.export.relore.progress",
										"Updated lore / display for ${i * chunkSize} / ${items.size}."
									)
								)
								i++
								key.forEach {
									fixLoreNbtFor(SkyblockId(it))
								}
							}
							MC.sendChat(tr("notfirmament.repo.export.relore.alldone", "All lores updated."))
						}
					}
				}
			}
		}
	}

	fun fixLoreNbtFor(itemid: SkyblockId) {
		modifyJson(itemid) {
			val mutJson = it.toMutableMap()
			val legacyTag = LegacyTagParser.parse(mutJson["nbttag"]!!.jsonPrimitive.content)
			val display = legacyTag.getCompoundOrEmpty("display")
			legacyTag.put("display", display)
			display.putString("Name", mutJson["displayname"]!!.jsonPrimitive.content)
			display.put(
				"Lore",
				(mutJson["lore"] as JsonArray).map { StringTag.valueOf(it.jsonPrimitive.content) }
					.toNbtList()
			)
			mutJson["nbttag"] = JsonPrimitive(legacyTag.toLegacyString())
			JsonObject(mutJson)
		}
	}

	@Subscribe
	fun onKeyBind(event: HandledScreenKeyPressedEvent) {
		if (event.matches(PowerUserTools.TConfig.exportItemStackToRepo)) {
			val itemStack = event.screen.focusedItemStack ?: return
			PowerUserTools.lastCopiedStack = (itemStack to exportItem(itemStack))
		}
	}

	val nonOverlayCache = mutableMapOf<SkyblockId, Boolean>()

	@Subscribe
	fun onRender(event: SlotRenderEvents.Before) {
		if (!PowerUserTools.TConfig.highlightNonOverlayItems) {
			return
		}
		val stack = event.slot.item ?: return
		val id = event.slot.item.skyBlockId?.neuItem
		if (PowerUserTools.TConfig.dontHighlightSemicolonItems && id != null && id.contains(";")) return
		val sbId = stack.skyBlockId ?: return
		val isExported = nonOverlayCache.getOrPut(sbId) {
			RepoManager.overlayData.getOverlayFiles(sbId).isNotEmpty() || // This extra case is here so that an export works immediately, without repo reload
				RepoDownloadManager.repoSavedLocation.resolve("itemsOverlay")
					.resolve(ExportedTestConstantMeta.current.dataVersion.toString())
					.resolve("${stack.skyBlockId}.snbt")
					.exists()
		}
		if (!isExported)
			event.context.drawGuiTexture(
				NotFirmament.identifier("selected_pet_background"),
				event.slot.x, event.slot.y, 16, 16,
			)
	}

	fun exportStub(skyblockId: SkyblockId, title: String, extra: (ItemStack) -> Unit = {}) {
		exportItem(ItemStack(Items.PLAYER_HEAD).also {
			it.displayNameAccordingToNbt = Component.literal(title)
			it.loreAccordingToNbt = listOf(Component.literal(""))
			it.setSkyBlockId(skyblockId)
			extra(it) // LOL
		})
		MC.sendChat(tr("notfirmament.repo.export.stub", "Exported a stub item for $skyblockId"))
	}
}
