package moe.nea.notfirmament.features.world

import moe.nea.notfirmament.annotations.Subscribe
import moe.nea.notfirmament.commands.thenExecute
import moe.nea.notfirmament.events.CommandEvent
import moe.nea.notfirmament.events.ReloadRegistrationEvent
import moe.nea.notfirmament.util.MoulConfigUtils
import moe.nea.notfirmament.util.ScreenUtil

object NPCWaypoints {

    var allNpcWaypoints = listOf<NavigableWaypoint>()

    @Subscribe
    fun onRepoReloadRegistration(event: ReloadRegistrationEvent) {
        event.repo.registerReloadListener {
            allNpcWaypoints = it.items.items.values
                .asSequence()
                .filter { !it.island.isNullOrBlank() }
                .map {
                    NavigableWaypoint.NPCWaypoint(it)
                }
                .toList()
        }
    }

    @Subscribe
    fun onOpenGui(event: CommandEvent.SubCommand) {
        event.subcommand("npcs") {
            thenExecute {
                ScreenUtil.setScreenLater(MoulConfigUtils.loadScreen(
                    "npc_waypoints",
                    NpcWaypointGui(allNpcWaypoints),
                    null))
            }
        }
    }


}
