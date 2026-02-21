package moe.nea.notfirmament.events

import io.github.moulberry.repo.NEURepository

data class ReloadRegistrationEvent(val repo: NEURepository) : NotFirmamentEvent() {
    companion object : NotFirmamentEventBus<ReloadRegistrationEvent>()
}
