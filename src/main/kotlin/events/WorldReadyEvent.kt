package moe.nea.notfirmament.events

class WorldReadyEvent : NotFirmamentEvent() {
	companion object : NotFirmamentEventBus<WorldReadyEvent>()
//	class FullyLoaded : NotFirmamentEvent() {
//		companion object : NotFirmamentEventBus<FullyLoaded>() {
//			 TODO: check WorldLoadingState
//		}
//	}
}
