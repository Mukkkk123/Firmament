package moe.nea.notfirmament.compat.iris

import net.fabricmc.loader.api.FabricLoader
import moe.nea.notfirmament.util.compatloader.CompatMeta
import moe.nea.notfirmament.util.compatloader.ICompatMeta

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return FabricLoader.getInstance().isModLoaded("iris")
	}
}
