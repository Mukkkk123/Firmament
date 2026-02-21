package moe.nea.notfirmament.util.mc

import net.minecraft.resources.Identifier

interface IntrospectableItemModelManager {
	fun hasModel_notfirmament(identifier: Identifier): Boolean
}
