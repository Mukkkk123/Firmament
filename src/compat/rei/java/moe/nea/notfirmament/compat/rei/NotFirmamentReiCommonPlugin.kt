package moe.nea.notfirmament.compat.rei

import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry
import me.shedaniel.rei.api.common.plugins.REICommonPlugin
import moe.nea.notfirmament.repo.RepoManager

class NotFirmamentReiCommonPlugin : REICommonPlugin {
	override fun registerEntryTypes(registry: EntryTypeRegistry) {
		if (!RepoManager.shouldLoadREI()) return
		registry.register(NotFirmamentReiPlugin.SKYBLOCK_ITEM_TYPE_ID, SBItemEntryDefinition)
	}
}
