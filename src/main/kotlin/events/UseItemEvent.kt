package moe.nea.notfirmament.events

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.InteractionHand
import net.minecraft.world.level.Level

data class UseItemEvent(val playerEntity: Player, val world: Level, val hand: InteractionHand) : NotFirmamentEvent.Cancellable() {
	companion object : NotFirmamentEventBus<UseItemEvent>()
	val item: ItemStack = playerEntity.getItemInHand(hand)
}
