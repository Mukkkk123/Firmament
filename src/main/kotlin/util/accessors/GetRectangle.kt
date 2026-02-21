

package moe.nea.notfirmament.util.accessors

import me.shedaniel.math.Rectangle
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import moe.nea.notfirmament.mixins.accessor.AccessorHandledScreen

fun AbstractContainerScreen<*>.getProperRectangle(): Rectangle {
    this.castAccessor()
    return Rectangle(
        getX_NotFirmament(),
        getY_NotFirmament(),
        getBackgroundWidth_NotFirmament(),
        getBackgroundHeight_NotFirmament()
    )
}
