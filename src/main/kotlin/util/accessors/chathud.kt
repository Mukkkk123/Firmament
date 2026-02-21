package moe.nea.notfirmament.util.accessors

import net.minecraft.client.gui.components.ChatComponent
import net.minecraft.client.GuiMessage
import moe.nea.notfirmament.mixins.accessor.AccessorChatHud

val ChatComponent.messages: MutableList<GuiMessage>
	get() = (this as AccessorChatHud).messages_notfirmament
