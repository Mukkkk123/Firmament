
package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import moe.nea.notfirmament.features.texturepack.CustomModelOverrideParser
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser
import net.minecraft.world.item.ItemStack

class OrPredicate(val children: Array<NotFirmamentModelPredicate>) : NotFirmamentModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        return children.any { it.test(stack) }
    }

    object Parser : NotFirmamentModelPredicateParser {
        override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate {
            val children =
                (jsonElement as JsonArray)
                    .flatMap {
	                    CustomModelOverrideParser.parsePredicates(it as JsonObject)
                    }
                    .toTypedArray()
            return OrPredicate(children)
        }

    }
}
