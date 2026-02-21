package moe.nea.notfirmament.mixins.custommodels;

import moe.nea.notfirmament.features.texturepack.HeadModelChooser;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemRenderStateExtraInfo implements HeadModelChooser.HasExplicitHeadModelMarker {
	@Unique
	boolean hasExplicitHead_notfirmament = false;

	@Inject(method = "clear", at = @At("HEAD"))
	private void clear(CallbackInfo ci) {
		hasExplicitHead_notfirmament = false;
	}

	@Override
	public void markExplicitHead_NotFirmament() {
		hasExplicitHead_notfirmament = true;
	}

	@Override
	public boolean isExplicitHeadModel_NotFirmament() {
		return hasExplicitHead_notfirmament;
	}
}
