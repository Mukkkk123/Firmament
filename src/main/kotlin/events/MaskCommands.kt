

package moe.nea.notfirmament.events

import com.mojang.brigadier.CommandDispatcher

data class MaskCommands(val dispatcher: CommandDispatcher<*>) : NotFirmamentEvent() {
    companion object : NotFirmamentEventBus<MaskCommands>()

    fun mask(name: String) {
        dispatcher.root.children.removeIf { it.name.equals(name, ignoreCase = true) }
    }
}
