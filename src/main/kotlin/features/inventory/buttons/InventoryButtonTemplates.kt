package moe.nea.notfirmament.features.inventory.buttons

import net.minecraft.network.chat.Component
import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.util.ErrorUtil
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.TemplateUtil

object InventoryButtonTemplates {

	val legacyPrefix = "NEUBUTTONS/"
	val modernPrefix = "MAYBEONEDAYIWILLHAVEMYOWNFORMAT"

	fun loadTemplate(t: String): List<InventoryButton>? {
		val buttons = TemplateUtil.maybeDecodeTemplate<List<String>>(legacyPrefix, t) ?: return null
		return buttons.mapNotNull {
			ErrorUtil.catch<InventoryButton?>("Could not import button") {
				NotFirmament.json.decodeFromString<InventoryButton>(it).also {
					if (it.icon?.startsWith("extra:") == true) {
						MC.sendChat(Component.translatable("notfirmament.inventory-buttons.import-failed"))
					}
				}
			}.or {
				null
			}
		}
	}

	fun saveTemplate(buttons: List<InventoryButton>): String {
		return TemplateUtil.encodeTemplate(legacyPrefix, buttons.map { NotFirmament.json.encodeToString(it) })
	}
}
