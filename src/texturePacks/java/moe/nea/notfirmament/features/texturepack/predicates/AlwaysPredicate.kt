
package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonElement
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser
import net.minecraft.world.item.ItemStack

object AlwaysPredicate : NotFirmamentModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        return true
    }

    object Parser : NotFirmamentModelPredicateParser {
        override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate {
            return AlwaysPredicate
        }
    }
}
