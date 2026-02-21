package moe.nea.notfirmament.compat.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import moe.nea.notfirmament.gui.config.AllConfigsGui

class NotFirmamentModMenuPlugin : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { AllConfigsGui.makeScreen(parent = it) }
    }
}
