package moe.nea.notfirmament.features.texturepack

import com.google.gson.JsonObject
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import moe.nea.notfirmament.features.texturepack.predicates.AndPredicate
import moe.nea.notfirmament.util.json.intoGson

object NotFirmamentRootPredicateSerializer : KSerializer<NotFirmamentModelPredicate> {
	val delegateSerializer = kotlinx.serialization.json.JsonObject.serializer()
	override val descriptor: SerialDescriptor
		get() = SerialDescriptor("NotFirmamentModelRootPredicate", delegateSerializer.descriptor)

	override fun deserialize(decoder: Decoder): NotFirmamentModelPredicate {
		val json = decoder.decodeSerializableValue(delegateSerializer).intoGson() as JsonObject
		return AndPredicate(CustomModelOverrideParser.parsePredicates(json).toTypedArray())
	}

	override fun serialize(encoder: Encoder, value: NotFirmamentModelPredicate) {
		TODO("Cannot serialize notfirmament predicates")
	}
}
