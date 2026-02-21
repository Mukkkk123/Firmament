package moe.nea.notfirmament.features.texturepack

import com.google.gson.JsonObject
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.Decoder
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.Encoder
import net.minecraft.client.renderer.item.ItemModels
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.Identifier
import moe.nea.notfirmament.NotFirmament
import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.events.FinalizeResourceManagerEvent
import moe.nea.notfirmament.features.texturepack.predicates.AndPredicate
import moe.nea.notfirmament.features.texturepack.predicates.CastPredicate
import moe.nea.notfirmament.features.texturepack.predicates.DisplayNamePredicate
import moe.nea.notfirmament.features.texturepack.predicates.ExtraAttributesPredicate
import moe.nea.notfirmament.features.texturepack.predicates.GenericComponentPredicate
import moe.nea.notfirmament.features.texturepack.predicates.ItemPredicate
import moe.nea.notfirmament.features.texturepack.predicates.LorePredicate
import moe.nea.notfirmament.features.texturepack.predicates.NotPredicate
import moe.nea.notfirmament.features.texturepack.predicates.OrPredicate
import moe.nea.notfirmament.features.texturepack.predicates.PetPredicate
import moe.nea.notfirmament.features.texturepack.predicates.PullingPredicate
import moe.nea.notfirmament.features.texturepack.predicates.SkullPredicate
import moe.nea.notfirmament.util.json.KJsonOps

object CustomModelOverrideParser {

	val LEGACY_CODEC: Codec<NotFirmamentModelPredicate> =
		Codec.of(
			Encoder.error("cannot encode legacy notfirmament model predicates"),
			object : Decoder<NotFirmamentModelPredicate> {
				override fun <T : Any?> decode(
					ops: DynamicOps<T>,
					input: T
				): DataResult<Pair<NotFirmamentModelPredicate, T>> {
					try {
						val pred = NotFirmament.json.decodeFromJsonElement(
							NotFirmamentRootPredicateSerializer,
							ops.convertTo(KJsonOps.INSTANCE, input))
						return DataResult.success(Pair.of(pred, ops.empty()))
					} catch (ex: Exception) {
						return DataResult.error { "Could not deserialize ${ex.message}" }
					}
				}
			}
		)

	val predicateParsers = mutableMapOf<Identifier, NotFirmamentModelPredicateParser>()


	fun registerPredicateParser(name: String, parser: NotFirmamentModelPredicateParser) {
		predicateParsers[Identifier.fromNamespaceAndPath("notfirmament", name)] = parser
	}

	init {
		registerPredicateParser("display_name", DisplayNamePredicate.Parser)
		registerPredicateParser("lore", LorePredicate.Parser)
		registerPredicateParser("all", AndPredicate.Parser)
		registerPredicateParser("any", OrPredicate.Parser)
		registerPredicateParser("not", NotPredicate.Parser)
		registerPredicateParser("item", ItemPredicate.Parser)
		registerPredicateParser("extra_attributes", ExtraAttributesPredicate.Parser)
		registerPredicateParser("pet", PetPredicate.Parser)
		registerPredicateParser("component", GenericComponentPredicate.Parser)
		registerPredicateParser("skull", SkullPredicate.Parser)
	}

	private val neverPredicate = listOf(
		object : NotFirmamentModelPredicate {
			override fun test(stack: ItemStack): Boolean {
				return false
			}
		}
	)

	fun parsePredicates(predicates: JsonObject?): List<NotFirmamentModelPredicate> {
		if (predicates == null) return neverPredicate
		val parsedPredicates = mutableListOf<NotFirmamentModelPredicate>()
		for (predicateName in predicates.keySet()) {
			if (predicateName == "cast") { // 1.21.4
				parsedPredicates.add(CastPredicate.Parser.parse(predicates[predicateName]) ?: return neverPredicate)
			}
			if (predicateName == "pull") {
				parsedPredicates.add(PullingPredicate.Parser.parse(predicates[predicateName]) ?: return neverPredicate)
			}
			if (predicateName == "pulling") {
				parsedPredicates.add(PullingPredicate.AnyPulling)
			}
			if (!predicateName.startsWith("notfirmament:")) continue
			val identifier = Identifier.parse(predicateName)
			val parser = predicateParsers[identifier] ?: return neverPredicate
			val parsedPredicate = parser.parse(predicates[predicateName]) ?: return neverPredicate
			parsedPredicates.add(parsedPredicate)
		}
		return parsedPredicates
	}

	@JvmStatic
	fun parseCustomModelOverrides(jsonObject: JsonObject): Array<NotFirmamentModelPredicate>? {
		val predicates = (jsonObject["predicate"] as? JsonObject) ?: return null
		val parsedPredicates = parsePredicates(predicates)
		if (parsedPredicates.isEmpty())
			return null
		return parsedPredicates.toTypedArray()
	}

	@Subscribe
	fun finalizeResources(event: FinalizeResourceManagerEvent) {
		ItemModels.ID_MAPPER.put(
			NotFirmament.identifier("predicates/legacy"),
			PredicateModel.Unbaked.CODEC
		)
		ItemModels.ID_MAPPER.put(
			NotFirmament.identifier("head_model"),
			HeadModelChooser.Unbaked.CODEC
		)
	}

}
