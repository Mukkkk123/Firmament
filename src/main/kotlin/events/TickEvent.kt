

package moe.nea.notfirmament.events

data class TickEvent(val tickCount: Int) : NotFirmamentEvent() {
	// TODO: introduce a client / server tick system.
	//       client ticks should ignore the game state
	//       server ticks should per-tick count packets received by the server
    companion object : NotFirmamentEventBus<TickEvent>()
}
