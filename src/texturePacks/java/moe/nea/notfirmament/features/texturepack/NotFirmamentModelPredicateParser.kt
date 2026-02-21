
package moe.nea.notfirmament.features.texturepack

import com.google.gson.JsonElement

interface NotFirmamentModelPredicateParser {
    fun parse(jsonElement: JsonElement): NotFirmamentModelPredicate?
}
