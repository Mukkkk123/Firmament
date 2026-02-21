package moe.nea.notfirmament.events

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.DeltaTracker
import net.minecraft.world.level.GameType
import moe.nea.notfirmament.util.MC

/**
 * Called when hud elements should be rendered, before the screen, but after the world.
 */
data class HudRenderEvent(val context: GuiGraphics, val tickDelta: DeltaTracker) : NotFirmamentEvent.Cancellable() {
	val isRenderingHud = !MC.options.hideGui
	val isRenderingCursor = MC.interactionManager?.playerMode != GameType.SPECTATOR && isRenderingHud

	init {
		if (!isRenderingHud)
			cancel()
	}

	companion object : NotFirmamentEventBus<HudRenderEvent>()
}
