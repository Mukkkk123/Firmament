package moe.nea.notfirmament.mixins.render.entitytints;

import moe.nea.notfirmament.events.EntityRenderTintEvent;
import moe.nea.notfirmament.util.render.TintedOverlayTexture;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class EntityRenderStateTint implements EntityRenderTintEvent.HasTintRenderState {
	@Unique
	int tint = -1;
	@Unique
	TintedOverlayTexture overlayTexture;
	@Unique
	boolean hasTintOverride = false;

	@Override
	public int getTint_notfirmament() {
		return tint;
	}

	@Override
	public void setTint_notfirmament(int i) {
		tint = i;
		hasTintOverride = true;
	}

	@Override
	public boolean getHasTintOverride_notfirmament() {
		return hasTintOverride;
	}

	@Override
	public void setHasTintOverride_notfirmament(boolean b) {
		hasTintOverride = b;
	}

	@Override
	public void reset_notfirmament() {
		hasTintOverride = false;
		overlayTexture = null;
	}

	@Override
	public @Nullable TintedOverlayTexture getOverlayTexture_notfirmament() {
		return overlayTexture;
	}

	@Override
	public void setOverlayTexture_notfirmament(@Nullable TintedOverlayTexture tintedOverlayTexture) {
		this.overlayTexture = tintedOverlayTexture;
	}
}
