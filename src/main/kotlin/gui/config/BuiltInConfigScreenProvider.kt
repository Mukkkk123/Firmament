package moe.nea.notfirmament.gui.config

import com.google.auto.service.AutoService
import net.minecraft.client.gui.screens.Screen

@AutoService(NotFirmamentConfigScreenProvider::class)
class BuiltInConfigScreenProvider : NotFirmamentConfigScreenProvider {
    override val key: String
        get() = "builtin"

    override fun open(search: String?, parent: Screen?): Screen {
        return AllConfigsGui.makeBuiltInScreen(parent)
    }
}
