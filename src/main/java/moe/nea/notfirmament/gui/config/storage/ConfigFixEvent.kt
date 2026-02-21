package moe.nea.notfirmament.gui.config.storage

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import moe.nea.notfirmament.events.NotFirmamentEvent
import moe.nea.notfirmament.events.NotFirmamentEventBus

data class ConfigFixEvent(
	val storageClass: ConfigStorageClass,
	val toVersion: Int,
	var data: JsonObject,
) : NotFirmamentEvent() {
	companion object : NotFirmamentEventBus<ConfigFixEvent>() {

	}
	fun on(
		toVersion: Int,
		storageClass: ConfigStorageClass,
		block: ConfigEditor.() -> Unit
	) {
		require(toVersion <= NotFirmamentConfigLoader.currentConfigVersion)
		if (this.toVersion == toVersion && this.storageClass == storageClass) {
			block(ConfigEditor(listOf(object : JsonPointer {
				override fun get(): JsonObject {
					return data
				}

				override fun set(value: JsonElement) {
					data = value as JsonObject
				}

				override fun toString(): String {
					return "ConfigRoot($storageClass)"
				}
			})))
		}
	}
}
