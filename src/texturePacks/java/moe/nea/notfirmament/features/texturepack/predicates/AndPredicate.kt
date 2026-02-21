package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.world.entity.LivingEntity
import moe.nea.notfirmament.features.texturepack.CustomModelOverrideParser
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser
import net.minecraft.world.item.ItemStack

class AndPredicate(val children: Array<NotFirmamentModelPredicate>) : NotFirmamentModelPredicate {
	override fun test(stack: ItemStack, holder: LivingEntity?): Boolean {
		return children.all { it.test(stack, holder) }
	}

    object Parser : NotFirmamentModelPredicateParser {
        override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate {
            val children =
                (jsonElement as JsonArray)
                    .flatMap {
	                    CustomModelOverrideParser.parsePredicates(it as JsonObject)
                    }
                    .toTypedArray()
            return AndPredicate(children)
        }

    }
}
