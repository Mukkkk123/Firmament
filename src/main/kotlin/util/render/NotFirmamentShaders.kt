package moe.nea.notfirmament.util.render

import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.events.DebugInstantiateEvent

object NotFirmamentShaders {

	@Subscribe
	fun debugLoad(event: DebugInstantiateEvent) {
		// TODO: do i still need to work with shaders like this?
	}
}
