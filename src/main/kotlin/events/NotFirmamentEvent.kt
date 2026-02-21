

package moe.nea.notfirmament.events

/**
 * An event that can be fired by a [NotFirmamentEventBus].
 *
 * Typically, that event bus is implemented as a companion object
 *
 * ```
 * class SomeEvent : NotFirmamentEvent() {
 *     companion object : NotFirmamentEventBus<SomeEvent>()
 * }
 * ```
 */
abstract class NotFirmamentEvent {
    /**
     * A [NotFirmamentEvent] that can be [cancelled]
     */
    abstract class Cancellable : NotFirmamentEvent() {
        /**
         * Cancels this is event.
         *
         * @see cancelled
         */
        fun cancel() {
            cancelled = true
        }

        /**
         * Whether this event is cancelled.
         *
         * Cancelled events will bypass handlers unless otherwise specified and will prevent the action that this
         * event was originally fired for.
         */
        var cancelled: Boolean = false
    }
}
