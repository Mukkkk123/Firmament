
package moe.nea.notfirmament.util.customgui

import net.minecraft.world.inventory.Slot

interface CoordRememberingSlot {
    fun rememberCoords_notfirmament()
    fun restoreCoords_notfirmament()
    fun getOriginalX_notfirmament(): Int
    fun getOriginalY_notfirmament(): Int
}

val Slot.originalX get() = (this as CoordRememberingSlot).getOriginalX_notfirmament()
val Slot.originalY get() = (this as CoordRememberingSlot).getOriginalY_notfirmament()
