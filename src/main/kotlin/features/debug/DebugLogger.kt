package moe.nea.notfirmament.features.debug

import kotlinx.serialization.serializer
import net.minecraft.network.chat.Component
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.TestUtil
import moe.nea.notfirmament.util.collections.InstanceList
import moe.nea.notfirmament.util.data.Config
import moe.nea.notfirmament.util.data.DataHolder

class DebugLogger(val tag: String) {
	companion object {
		val allInstances = InstanceList<DebugLogger>("DebugLogger")
	}

	@Config
	object EnabledLogs : DataHolder<MutableSet<String>>(serializer(), "DebugLogs", ::mutableSetOf)

	init {
		allInstances.add(this)
	}

	fun isEnabled() = TestUtil.isInTest || EnabledLogs.data.contains(tag)
	fun log(text: String) = log { text }
	fun log(text: () -> String) {
		if (!isEnabled()) return
		MC.sendChat(Component.literal(text()))
	}
}
