
package moe.nea.notfirmament.events

import net.minecraft.world.entity.Entity

data class EntityDespawnEvent(
    val entity: Entity?, val entityId: Int,
    val reason: Entity.RemovalReason,
) : NotFirmamentEvent() {
    companion object: NotFirmamentEventBus<EntityDespawnEvent>()
}
