package moe.nea.notfirmament.events

import net.minecraft.world.item.ItemStack
import moe.nea.notfirmament.util.MC

sealed class ChestInventoryUpdateEvent : NotFirmamentEvent() {
	companion object : NotFirmamentEventBus<ChestInventoryUpdateEvent>()
	data class Single(val slot: Int, val stack: ItemStack) : ChestInventoryUpdateEvent()
	data class Multi(val contents: List<ItemStack>) : ChestInventoryUpdateEvent()
	val inventory = MC.screen
}
