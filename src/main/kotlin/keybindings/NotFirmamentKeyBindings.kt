package moe.nea.notfirmament.keybindings

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import com.mojang.blaze3d.platform.InputConstants
import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.gui.config.ManagedOption
import moe.nea.notfirmament.util.TestUtil
import moe.nea.notfirmament.util.data.ManagedConfig

object NotFirmamentKeyBindings {
	val cats = mutableMapOf<ManagedConfig.Category, KeyMapping.Category>()


	fun registerKeyBinding(name: String, config: ManagedOption<SavedKeyBinding>) {
		val vanillaKeyBinding = KeyMapping(
			name,
			InputConstants.Type.KEYSYM,
			-1,
			cats.computeIfAbsent(config.element.category) {
				KeyMapping.Category.register(NotFirmament.identifier(it.name.lowercase()))
			}
		)
		if (!TestUtil.isInTest) {
			KeyBindingHelper.registerKeyBinding(vanillaKeyBinding)
		}
		keyBindings[vanillaKeyBinding] = config
	}

	val keyBindings = mutableMapOf<KeyMapping, ManagedOption<SavedKeyBinding>>()

}
