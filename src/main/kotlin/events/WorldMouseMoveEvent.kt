package moe.nea.notfirmament.events

data class WorldMouseMoveEvent(val deltaX: Double, val deltaY: Double) : NotFirmamentEvent.Cancellable() {
	companion object : NotFirmamentEventBus<WorldMouseMoveEvent>()
}
