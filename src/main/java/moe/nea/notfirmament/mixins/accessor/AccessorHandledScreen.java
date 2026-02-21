package moe.nea.notfirmament.mixins.accessor;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AccessorHandledScreen {
    @Accessor("hoveredSlot")
    @Nullable
	Slot getFocusedSlot_NotFirmament();

    @Accessor("imageWidth")
    int getBackgroundWidth_NotFirmament();

    @Accessor("imageWidth")
    void setBackgroundWidth_NotFirmament(int newBackgroundWidth);

    @Accessor("imageHeight")
    int getBackgroundHeight_NotFirmament();

    @Accessor("imageHeight")
    void setBackgroundHeight_NotFirmament(int newBackgroundHeight);

    @Accessor("leftPos")
    int getX_NotFirmament();

    @Accessor("leftPos")
    void setX_NotFirmament(int newX);

    @Accessor("topPos")
    int getY_NotFirmament();

    @Accessor("topPos")
    void setY_NotFirmament(int newY);

}
