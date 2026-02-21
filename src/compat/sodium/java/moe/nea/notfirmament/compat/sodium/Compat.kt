package moe.nea.notfirmament.compat.sodium

import moe.nea.notfirmament.util.compatloader.CompatMeta
import moe.nea.notfirmament.util.compatloader.ICompatMeta
import net.fabricmc.loader.api.FabricLoader

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return FabricLoader.getInstance().isModLoaded("sodium")
	}
}
