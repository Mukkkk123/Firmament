

package moe.nea.notfirmament.events

import net.minecraft.client.gui.screens.Screen

data class ScreenChangeEvent(val old: Screen?, val new: Screen?) : NotFirmamentEvent.Cancellable() {
    var overrideScreen: Screen? = null
    companion object : NotFirmamentEventBus<ScreenChangeEvent>()
}
