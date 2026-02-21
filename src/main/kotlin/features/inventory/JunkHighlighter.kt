package moe.nea.notfirmament.features.inventory

import org.lwjgl.glfw.GLFW
import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.events.SlotRenderEvents
import moe.nea.notfirmament.util.data.Config
import moe.nea.notfirmament.util.data.ManagedConfig
import moe.nea.notfirmament.util.skyblock.SBItemUtil.getSearchName
import moe.nea.notfirmament.util.useMatch

object JunkHighlighter {
	val identifier: String
		get() = "junk-highlighter"

	@Config
	object TConfig : ManagedConfig(identifier, Category.INVENTORY) {
		val junkRegex by string("regex") { "" }
		val highlightBind by keyBinding("highlight") { GLFW.GLFW_KEY_LEFT_CONTROL }
	}

	@Subscribe
	fun onDrawSlot(event: SlotRenderEvents.After) {
		if (!TConfig.highlightBind.isPressed() || TConfig.junkRegex.isEmpty()) return
		val junkRegex = TConfig.junkRegex.toPattern()
		val slot = event.slot
		junkRegex.useMatch(slot.item.getSearchName()) {
			event.context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, 0xffff0000.toInt())
		}
	}
}
