
package moe.nea.notfirmament.events.subscription

import moe.nea.notfirmament.events.NotFirmamentEvent
import moe.nea.notfirmament.events.NotFirmamentEventBus


data class Subscription<T : NotFirmamentEvent>(
    val owner: Any,
    val invoke: (T) -> Unit,
    val eventBus: NotFirmamentEventBus<T>,
    val methodName: String,
)
