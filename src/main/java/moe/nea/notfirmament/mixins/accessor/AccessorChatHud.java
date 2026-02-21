package moe.nea.notfirmament.mixins.accessor;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.GuiMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(ChatComponent.class)
public interface AccessorChatHud {
	@Accessor("allMessages")
	List<GuiMessage> getMessages_notfirmament();

	@Accessor("trimmedMessages")
	List<GuiMessage.Line> getVisibleMessages_notfirmament();

	@Accessor("chatScrollbarPos")
	int getScrolledLines_notfirmament();
}
