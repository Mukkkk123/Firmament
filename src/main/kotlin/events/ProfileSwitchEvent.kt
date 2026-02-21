package moe.nea.notfirmament.events

import java.util.UUID

data class ProfileSwitchEvent(val oldProfile: UUID?, val newProfile: UUID?) : NotFirmamentEvent() {
	companion object : NotFirmamentEventBus<ProfileSwitchEvent>()
}
