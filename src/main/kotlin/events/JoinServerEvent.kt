package moe.nea.notfirmament.events

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.multiplayer.ClientPacketListener

data class JoinServerEvent(
    val networkHandler: ClientPacketListener,
    val packetSender: PacketSender,
) : NotFirmamentEvent() {
	companion object : NotFirmamentEventBus<JoinServerEvent>()
}
