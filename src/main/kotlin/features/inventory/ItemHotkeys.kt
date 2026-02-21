package moe.nea.notfirmament.features.inventory

import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.events.HandledScreenKeyPressedEvent
import moe.nea.notfirmament.repo.ExpensiveItemCacheApi
import moe.nea.notfirmament.repo.HypixelStaticData
import moe.nea.notfirmament.repo.ItemCache.asItemStack
import moe.nea.notfirmament.repo.ItemCache.isBroken
import moe.nea.notfirmament.repo.RepoManager
import moe.nea.notfirmament.util.MC
import moe.nea.notfirmament.util.asBazaarStock
import moe.nea.notfirmament.util.data.Config
import moe.nea.notfirmament.util.data.ManagedConfig
import moe.nea.notfirmament.util.focusedItemStack
import moe.nea.notfirmament.util.skyBlockId
import moe.nea.notfirmament.util.skyblock.SBItemUtil.getSearchName

object ItemHotkeys {
	@Config
	object TConfig : ManagedConfig("item-hotkeys", Category.INVENTORY) {
		val openGlobalTradeInterface by keyBindingWithDefaultUnbound("global-trade-interface")
	}

	@OptIn(ExpensiveItemCacheApi::class)
	@Subscribe
	fun onHandledInventoryPress(event: HandledScreenKeyPressedEvent) {
		if (!event.matches(TConfig.openGlobalTradeInterface)) {
			return
		}
		var item = event.screen.focusedItemStack ?: return
		val skyblockId = item.skyBlockId ?: return
		item = RepoManager.getNEUItem(skyblockId)?.asItemStack()?.takeIf { !it.isBroken } ?: item
		if (HypixelStaticData.hasBazaarStock(skyblockId.asBazaarStock)) {
			MC.sendCommand("bz ${item.getSearchName()}")
		} else if (HypixelStaticData.hasAuctionHouseOffers(skyblockId)) {
			MC.sendCommand("ahs ${item.getSearchName()}")
		} else {
			return
		}
		event.cancel()
	}

}
