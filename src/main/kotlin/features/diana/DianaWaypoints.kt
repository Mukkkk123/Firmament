package moe.nea.notfirmament.features.diana

import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.events.AttackBlockEvent
import moe.nea.notfirmament.events.UseBlockEvent
import moe.nea.notfirmament.util.data.Config
import moe.nea.notfirmament.util.data.ManagedConfig

object DianaWaypoints {
	val identifier get() = "diana"

	@Config
	object TConfig : ManagedConfig(identifier, Category.EVENTS) {
		val ancestralSpadeSolver by toggle("ancestral-spade") { true }
		val ancestralSpadeTeleport by keyBindingWithDefaultUnbound("ancestral-teleport")
		val nearbyWaypoints by toggle("nearby-waypoints") { true }
	}


	@Subscribe
	fun onBlockUse(event: UseBlockEvent) {
		NearbyBurrowsSolver.onBlockClick(event.hitResult.blockPos)
	}

	@Subscribe
	fun onBlockAttack(event: AttackBlockEvent) {
		NearbyBurrowsSolver.onBlockClick(event.blockPos)
	}
}


