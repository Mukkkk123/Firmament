package moe.nea.notfirmament.features

import moe.nea.notfirmament.events.NotFirmamentEvent
import moe.nea.notfirmament.events.subscription.Subscription
import moe.nea.notfirmament.events.subscription.SubscriptionList
import moe.nea.notfirmament.util.ErrorUtil
import moe.nea.notfirmament.util.compatloader.ICompatMeta

object FeatureManager {

	fun subscribeEvents() {
		SubscriptionList.allLists.forEach { list ->
			if (ICompatMeta.shouldLoad(list.javaClass.name))
				ErrorUtil.catch("Error while loading events from $list") {
					list.provideSubscriptions {
						subscribeSingleEvent(it)
					}
				}
		}
	}

	private fun <T : NotFirmamentEvent> subscribeSingleEvent(it: Subscription<T>) {
		it.eventBus.subscribe(false, "${it.owner.javaClass.simpleName}:${it.methodName}", it.invoke)
	}
}
