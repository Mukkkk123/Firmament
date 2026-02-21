package moe.nea.notfirmament.features.debug

import net.minecraft.network.chat.Component
import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.commands.thenExecute
import moe.nea.notfirmament.commands.thenLiteral
import moe.nea.notfirmament.events.CommandEvent
import moe.nea.notfirmament.events.SoundReceiveEvent
import moe.nea.notfirmament.events.WorldReadyEvent
import moe.nea.notfirmament.events.WorldRenderLastEvent
import moe.nea.notfirmament.util.red
import moe.nea.notfirmament.util.render.RenderInWorldContext

object SoundVisualizer {

	var showSounds = false

	var sounds = mutableListOf<SoundReceiveEvent>()


	@Subscribe
	fun onSubCommand(event: CommandEvent.SubCommand) {
		event.subcommand(DeveloperFeatures.DEVELOPER_SUBCOMMAND) {
			thenLiteral("sounds") {
				thenExecute {
					showSounds = !showSounds
					if (!showSounds) {
						sounds.clear()
					}
				}
			}
		}
	}

	@Subscribe
	fun onWorldSwap(event: WorldReadyEvent) {
		sounds.clear()
	}

	@Subscribe
	fun onRender(event: WorldRenderLastEvent) {
		RenderInWorldContext.renderInWorld(event) {
			sounds.forEach { event ->
				withFacingThePlayer(event.position) {
					text(
						Component.literal(event.sound.value().location.toString()).also {
							if (event.cancelled)
								it.red()
						},
						verticalAlign = RenderInWorldContext.VerticalAlign.CENTER,
					)
				}
			}
		}
	}

	@Subscribe
	fun onSoundReceive(event: SoundReceiveEvent) {
		if (!showSounds) return
		if (sounds.size > 1000) {
			sounds.subList(0, 200).clear()
		}
		sounds.add(event)
	}
}
