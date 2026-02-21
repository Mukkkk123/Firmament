package moe.nea.notfirmament.features.texturepack.predicates

import com.google.gson.JsonElement
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicate
import moe.nea.notfirmament.features.texturepack.NotFirmamentModelPredicateParser

class PullingPredicate(val percentage: Double) : NotFirmamentModelPredicate {
	companion object {
		val AnyPulling = PullingPredicate(0.1)
	}

	object Parser : NotFirmamentModelPredicateParser {
		override fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate? {
			return PullingPredicate(jsonElement.asDouble)
		}
	}

	override fun test(stack: ItemStack, holder: LivingEntity?): Boolean {
		if (holder == null) return false
		return BowItem.getPowerForTime(holder.ticksUsingItem) >= percentage
	}

}
