
package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonElement
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser
import moe.nea.notfirmament.features.texturepack.StringMatcher
import net.minecraft.world.item.ItemStack
import moe.nea.notfirmament.util.mc.displayNameAccordingToNbt

data class DisplayNamePredicate(val stringMatcher: StringMatcher) : NotFirmamentModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        val display = stack.displayNameAccordingToNbt
        return stringMatcher.matches(display)
    }

    object Parser : NotFirmamentModelPredicateParser {
        override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate {
            return DisplayNamePredicate(StringMatcher.parse(jsonElement))
        }
    }
}
