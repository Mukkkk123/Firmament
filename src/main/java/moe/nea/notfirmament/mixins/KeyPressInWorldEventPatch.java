

package moe.nea.notfirmament.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import moe.nea.notfirmament.events.WorldKeyboardEvent;
import moe.nea.notfirmament.keybindings.GenericInputAction;
import moe.nea.notfirmament.keybindings.InputModifiers;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardHandler.class)
public class KeyPressInWorldEventPatch {

	@WrapWithCondition(method = "keyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;click(Lcom/mojang/blaze3d/platform/InputConstants$Key;)V"))
	public boolean onKeyBoardInWorld(InputConstants.Key key, @Local(argsOnly = true) KeyEvent keyInput) {
		var event = WorldKeyboardEvent.Companion.publish(new WorldKeyboardEvent(GenericInputAction.of(keyInput), InputModifiers.of(keyInput)));
		return !event.getCancelled();
	}
}
