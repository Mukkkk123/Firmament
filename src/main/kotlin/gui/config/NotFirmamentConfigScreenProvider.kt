package moe.nea.notfirmament.gui.config

import net.minecraft.client.gui.screens.Screen
import moe.nea.notfirmament.util.compatloader.CompatLoader

interface NotFirmamentConfigScreenProvider {
	val key: String
	val isEnabled: Boolean get() = true

	fun open(search: String?, parent: Screen?): Screen

	companion object : CompatLoader<NotFirmamentConfigScreenProvider>(NotFirmamentConfigScreenProvider::class) {
		val providers by lazy {
			allValidInstances
				.filter { it.isEnabled }
				.sortedWith(
					Comparator
						.comparing<NotFirmamentConfigScreenProvider, Boolean>({ it.key == "builtin" })
						.reversed()
						.then(Comparator.comparing({ it.key }))
				).toList()
		}
	}
}
