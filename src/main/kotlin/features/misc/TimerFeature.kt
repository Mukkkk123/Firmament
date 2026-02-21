package moe.nea.notfirmament.features.misc

import com.mojang.brigadier.arguments.IntegerArgumentType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.commands.DurationArgumentType
import moe.nea.notfirmament.commands.RestArgumentType
import moe.nea.notfirmament.commands.get
import moe.nea.notfirmament.commands.thenArgument
import moe.nea.notfirmament.commands.thenExecute
import moe.nea.notfirmament.events.CommandEvent
import moe.nea.notfirmament.events.TickEvent
import moe.nea.notfirmament.util.CommonSoundEffects
import moe.nea.notfirmament.util.FirmFormatters
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.MinecraftDispatcher
import moe.nea.notfirmament.util.TimeMark
import moe.nea.notfirmament.util.clickCommand
import moe.nea.notfirmament.util.lime
import moe.nea.notfirmament.util.red
import moe.nea.notfirmament.util.tr
import moe.nea.notfirmament.util.yellow

object TimerFeature {
	data class Timer(
		val start: TimeMark,
		val duration: Duration,
		val message: String,
		val timerId: Int,
	) {
		fun timeLeft() = (duration - start.passedTime()).coerceAtLeast(0.seconds)
		fun isDone() = start.passedTime() >= duration
	}

	// Theoretically for optimal performance this could be a treeset keyed to the end time
	val timers = mutableListOf<Timer>()

	@Subscribe
	fun tick(event: TickEvent) {
		timers.removeAll {
			if (it.isDone()) {
				MC.sendChat(tr("notfirmament.timer.finished",
				               "The timer you set ${FirmFormatters.formatTimespan(it.duration)} ago just went off: ${it.message}")
					            .yellow())
				NotFirmament.coroutineScope.launch {
					withContext(MinecraftDispatcher) {
						repeat(5) {
							CommonSoundEffects.playSuccess()
							delay(0.2.seconds)
						}
					}
				}
				true
			} else {
				false
			}
		}
	}

	fun startTimer(duration: Duration, message: String) {
		val timerId = createTimerId++
		timers.add(Timer(TimeMark.now(), duration, message, timerId))
		MC.sendChat(
			tr("notfirmament.timer.start",
			   "Timer started for $message in ${FirmFormatters.formatTimespan(duration)}.").lime()
				.append(" ")
				.append(
					tr("notfirmament.timer.cancelbutton",
					   "Click here to cancel the timer."
					).clickCommand("/firm timer clear $timerId").red()
				)
		)
	}

	fun clearTimer(timerId: Int) {
		val timer = timers.indexOfFirst { it.timerId == timerId }
		if (timer < 0) {
			MC.sendChat(tr("notfirmament.timer.cancel.fail",
			               "Could not cancel that timer. Maybe it was already cancelled?").red())
		} else {
			val timerData = timers[timer]
			timers.removeAt(timer)
			MC.sendChat(tr("notfirmament.timer.cancel.done",
			               "Cancelled timer ${timerData.message}. It would have been done in ${
				               FirmFormatters.formatTimespan(timerData.timeLeft())
			               }.").lime())
		}
	}

	var createTimerId = 0

	@Subscribe
	fun onCommands(event: CommandEvent.SubCommand) {
		event.subcommand("cleartimer") {
			thenArgument("timerId", IntegerArgumentType.integer(0)) { timerId ->
				thenExecute {
					clearTimer(this[timerId])
				}
			}
			thenExecute {
				timers.map { it.timerId }.forEach {
					clearTimer(it)
				}
			}
		}
		event.subcommand("timer") {
			thenArgument("time", DurationArgumentType) { duration ->
				thenExecute {
					startTimer(this[duration], "no message")
				}
				thenArgument("message", RestArgumentType) { message ->
					thenExecute {
						startTimer(this[duration], this[message])
					}
				}
			}
		}
	}
}
