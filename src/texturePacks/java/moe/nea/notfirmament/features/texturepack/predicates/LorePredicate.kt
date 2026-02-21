
package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonElement
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser
import moe.nea.notfirmament.features.texturepack.StringMatcher
import net.minecraft.world.item.ItemStack
import moe.nea.notfirmament.util.mc.loreAccordingToNbt

class LorePredicate(val matcher: StringMatcher) : NotFirmamentModelPredicate {
    object Parser : NotFirmamentModelPredicateParser {
        override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate {
            return LorePredicate(StringMatcher.parse(jsonElement))
        }
    }

    override fun test(stack: ItemStack): Boolean {
        val lore = stack.loreAccordingToNbt
        return lore.any { matcher.matches(it) }
    }
}
