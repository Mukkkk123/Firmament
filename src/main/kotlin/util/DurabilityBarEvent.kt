
package moe.nea.notfirmament.util

import me.shedaniel.math.Color
import net.minecraft.world.item.ItemStack
import moe.nea.notfirmament.events.NotFirmamentEvent
import moe.nea.notfirmament.events.NotFirmamentEventBus

data class DurabilityBarEvent(
    val item: ItemStack,
) : NotFirmamentEvent() {
    data class DurabilityBar(
        val color: Color,
        val percentage: Float,
    )

    var barOverride: DurabilityBar? = null

    companion object : NotFirmamentEventBus<DurabilityBarEvent>()
}
