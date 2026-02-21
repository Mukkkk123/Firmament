package moe.nea.notfirmament.gui.config.storage

import com.google.gson.JsonElement

interface JsonPointer {
	fun get(): JsonElement
	fun set(value: JsonElement)
}
