

package moe.nea.notfirmament.events

import me.shedaniel.math.Rectangle
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen

data class HandledScreenPushREIEvent(
    val screen: AbstractContainerScreen<*>,
    val rectangles: MutableList<Rectangle> = mutableListOf()
) : NotFirmamentEvent() {

    fun block(rectangle: Rectangle) {
        rectangles.add(rectangle)
    }

    companion object : NotFirmamentEventBus<HandledScreenPushREIEvent>()
}
