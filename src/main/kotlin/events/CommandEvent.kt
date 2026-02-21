

package moe.nea.notfirmament.events

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.commands.CommandBuildContext
import moe.nea.notfirmament.commands.CaseInsensitiveLiteralCommandNode
import moe.nea.notfirmament.commands.DefaultSource
import moe.nea.notfirmament.commands.literal
import moe.nea.notfirmament.commands.thenLiteral

data class CommandEvent(
    val dispatcher: CommandDispatcher<DefaultSource>,
    val ctx: CommandBuildContext,
    val serverCommands: CommandDispatcher<*>?,
) : NotFirmamentEvent() {
    companion object : NotFirmamentEventBus<CommandEvent>()

    /**
     * Register subcommands to `/firm`. For new top level commands use [CommandEvent]. Cannot be used to register
     * subcommands to other commands.
     */
    data class SubCommand(
        val builder: CaseInsensitiveLiteralCommandNode.Builder<DefaultSource>,
        val commandRegistryAccess: CommandBuildContext,
    ) : NotFirmamentEvent() {
        companion object : NotFirmamentEventBus<SubCommand>()

        fun subcommand(name: String, block: CaseInsensitiveLiteralCommandNode.Builder<DefaultSource>.() -> Unit) {
            builder.thenLiteral(name, block)
        }
    }

    fun deleteCommand(name: String) {
        dispatcher.root.children.removeIf { it.name.equals(name, ignoreCase = false) }
        serverCommands?.root?.children?.removeIf { it.name.equals(name, ignoreCase = false) }
    }

    fun register(
        name: String,
        block: CaseInsensitiveLiteralCommandNode.Builder<DefaultSource>.() -> Unit
    ): LiteralCommandNode<DefaultSource> {
        return dispatcher.register(literal(name, block))
    }
}
