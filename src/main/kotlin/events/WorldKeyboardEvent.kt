package moe.nea.notfirmament.events

import moe.nea.notfirmament.keybindings.GenericInputAction
import moe.nea.notfirmament.keybindings.InputModifiers
import moe.nea.notfirmament.keybindings.SavedKeyBinding

data class WorldKeyboardEvent(val action: GenericInputAction, val modifiers: InputModifiers) : NotFirmamentEvent.Cancellable() {
	fun matches(keyBinding: SavedKeyBinding, atLeast: Boolean = false): Boolean {
		return keyBinding.matches(action, modifiers, atLeast)
	}

	companion object : NotFirmamentEventBus<WorldKeyboardEvent>()
}
