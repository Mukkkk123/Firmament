package moe.nea.notfirmament.compat.jade

import moe.nea.notfirmament.util.SBData

fun isOnMiningIsland(): Boolean =
	SBData.skyblockLocation?.hasCustomMining ?: false
