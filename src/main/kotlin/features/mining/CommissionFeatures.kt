package moe.nea.notfirmament.features.mining

import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.events.SlotRenderEvents
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.data.Config
import moe.nea.notfirmament.util.data.ManagedConfig
import moe.nea.notfirmament.util.mc.loreAccordingToNbt
import moe.nea.notfirmament.util.unformattedString

object CommissionFeatures {
	@Config
	object TConfig : ManagedConfig("commissions", Category.MINING) {
		val highlightCompletedCommissions by toggle("highlight-completed") { true }
	}


	@Subscribe
	fun onSlotRender(event: SlotRenderEvents.Before) {
		if (!TConfig.highlightCompletedCommissions) return
		if (MC.screenName != "Commissions") return
		val stack = event.slot.item
		if (stack.loreAccordingToNbt.any { it.unformattedString == "COMPLETED" }) {
			event.highlight(NotFirmament.identifier("completed_commission_background"))
		}
	}
}
