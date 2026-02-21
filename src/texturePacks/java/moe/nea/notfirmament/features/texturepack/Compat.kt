package moe.nea.notfirmament.features.texturepack

import moe.nea.notfirmament.util.compatloader.CompatMeta
import moe.nea.notfirmament.util.compatloader.ICompatMeta

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return true
	}
}
