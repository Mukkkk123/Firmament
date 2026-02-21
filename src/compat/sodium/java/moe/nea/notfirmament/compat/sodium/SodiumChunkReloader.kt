package moe.nea.notfirmament.compat.sodium

import moe.nea.notfirmament.mixins.sodium.accessor.AccessorSodiumWorldRenderer
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer

class SodiumChunkReloader : Runnable {
    override fun run() {
        (SodiumWorldRenderer.instanceNullable() as? AccessorSodiumWorldRenderer)
            ?.renderSectionManager_notfirmament
            ?.markGraphDirty()
    }
}
