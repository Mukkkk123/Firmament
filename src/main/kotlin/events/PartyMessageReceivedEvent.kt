package moe.nea.notfirmament.events

data class PartyMessageReceivedEvent(
	val from: ProcessChatEvent,
	val message: String,
	val name: String,
) : NotFirmamentEvent() {
	companion object : NotFirmamentEventBus<PartyMessageReceivedEvent>()
}
