
package moe.nea.notfirmament.events

import net.minecraft.core.Holder
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.phys.Vec3

data class SoundReceiveEvent(
    val sound: Holder<SoundEvent>,
    val category: SoundSource,
    val position: Vec3,
    val pitch: Float,
    val volume: Float,
    val seed: Long
) : NotFirmamentEvent.Cancellable() {
    companion object : NotFirmamentEventBus<SoundReceiveEvent>()
}
