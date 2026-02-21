
package moe.nea.notfirmament.util.customgui

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen

@Suppress("FunctionName")
interface HasCustomGui {
    fun getCustomGui_NotFirmament(): CustomGui?
    fun setCustomGui_NotFirmament(gui: CustomGui?)
}

var <T : AbstractContainerScreen<*>> T.customGui: CustomGui?
    get() = (this as HasCustomGui).getCustomGui_NotFirmament()
    set(value) {
        (this as HasCustomGui).setCustomGui_NotFirmament(value)
    }

