
package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import moe.nea.notfirmament.features.texturepack.CustomModelOverrideParser
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser
import net.minecraft.world.item.ItemStack

class NotPredicate(val children: Array<NotFirmamentModelPredicate>) : NotFirmamentModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        return children.none { it.test(stack) }
    }

    object Parser : NotFirmamentModelPredicateParser {
        override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate {
            return NotPredicate(CustomModelOverrideParser.parsePredicates(jsonElement as JsonObject).toTypedArray())
        }
    }
}
