package moe.nea.notfirmament.events

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen

data class HandledScreenClickEvent(
	val screen: AbstractContainerScreen<*>,
	val mouseX: Double, val mouseY: Double, val button: Int
) :
	NotFirmamentEvent.Cancellable() {
	companion object : NotFirmamentEventBus<HandledScreenClickEvent>()
}
