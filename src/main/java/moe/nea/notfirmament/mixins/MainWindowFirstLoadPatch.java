package moe.nea.notfirmament.mixins;

import moe.nea.notfirmament.NotFirmament;
import moe.nea.notfirmament.events.DebugInstantiateEvent;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MainWindowFirstLoadPatch {
	@Unique
	private static boolean hasInited = false;

	@Inject(method = "<init>(ZLnet/minecraft/client/gui/components/LogoRenderer;)V", at = @At("RETURN"))
	private void onCreate(boolean doBackgroundFade, LogoRenderer logoDrawer, CallbackInfo ci) {
		if (!hasInited && NotFirmament.INSTANCE.getDEBUG()) {
			try {
				DebugInstantiateEvent.Companion.publish(new DebugInstantiateEvent());
			} catch (Throwable t) {
				NotFirmament.INSTANCE.getLogger().error("Failed to instantiate debug instances", t);
				System.exit(1);
				throw t;
			}
		}
		hasInited = true;
	}
}
