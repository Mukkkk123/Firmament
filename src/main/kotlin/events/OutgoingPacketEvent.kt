

package moe.nea.notfirmament.events

import net.minecraft.network.protocol.Packet

data class OutgoingPacketEvent(val packet: Packet<*>) : NotFirmamentEvent.Cancellable() {
    companion object : NotFirmamentEventBus<OutgoingPacketEvent>()
}
