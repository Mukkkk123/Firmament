package moe.nea.notfirmament

import moe.nea.notfirmament.util.compatloader.CompatMeta
import moe.nea.notfirmament.util.compatloader.ICompatMeta

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return true
	}
}
