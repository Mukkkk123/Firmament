package moe.nea.notfirmament.impl.v1

import java.util.Collections
import java.util.Optional
import net.fabricmc.loader.api.FabricLoader
import kotlin.jvm.optionals.getOrNull
import net.minecraft.world.item.ItemStack
import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.api.v1.NotFirmamentAPI
import moe.nea.notfirmament.api.v1.NotFirmamentExtension
import moe.nea.notfirmament.api.v1.NotFirmamentItemWidget
import moe.nea.notfirmament.features.items.recipes.ItemList
import moe.nea.notfirmament.repo.ExpensiveItemCacheApi
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.intoOptional

object NotFirmamentAPIImpl : NotFirmamentAPI() {
	@JvmField
	val INSTANCE: NotFirmamentAPI = NotFirmamentAPIImpl

	private val _extensions = mutableListOf<NotFirmamentExtension>()
	override fun getExtensions(): List<NotFirmamentExtension> {
		return Collections.unmodifiableList(_extensions)
	}

	@OptIn(ExpensiveItemCacheApi::class)
	override fun getHoveredItemWidget(): Optional<NotFirmamentItemWidget> {
		val mouse = MC.instance.mouseHandler
		val window = MC.window
		val xpos = mouse.getScaledXPos(window)
		val ypos = mouse.getScaledYPos(window)
		val widget = MC.screen
			?.getChildAt(xpos, ypos)
			?.getOrNull()
		if (widget is NotFirmamentItemWidget) return widget.intoOptional()
		val itemListStack = ItemList.findStackUnder(xpos.toInt(), ypos.toInt())
		if (itemListStack != null)
			return object : NotFirmamentItemWidget {
				override fun getPlacement(): NotFirmamentItemWidget.Placement {
					return NotFirmamentItemWidget.Placement.ITEM_LIST
				}

				override fun getItemStack(): ItemStack {
					return itemListStack.second.asImmutableItemStack()
				}

				override fun getSkyBlockId(): String {
					return itemListStack.second.skyblockId.neuItem
				}

			}.intoOptional()
		return Optional.empty()
	}

	fun loadExtensions() {
		for (container in FabricLoader.getInstance()
			.getEntrypointContainers(NotFirmamentExtension.ENTRYPOINT_NAME, NotFirmamentExtension::class.java)) {
			NotFirmament.logger.info("Loading extension ${container.entrypoint} from ${container.provider.metadata.name}")
			loadExtension(container.entrypoint)
		}
		extensions.forEach { it.onLoad() }
	}

	fun loadExtension(entrypoint: NotFirmamentExtension) {
		_extensions.add(entrypoint)
	}
}
