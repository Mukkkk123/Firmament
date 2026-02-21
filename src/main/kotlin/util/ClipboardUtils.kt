

package moe.nea.notfirmament.util

import moe.nea.notfirmament.NotFirmament

object ClipboardUtils {
    fun setTextContent(string: String) {
        try {
            MC.keyboard.clipboard = string.ifEmpty { " " }
        } catch (e: Exception) {
            NotFirmament.logger.error("Could not write clipboard", e)
        }
    }

    fun getTextContents(): String {
        try {
            return MC.keyboard.clipboard ?: ""
        } catch (e: Exception) {
            NotFirmament.logger.error("Could not read clipboard", e)
            return ""
        }
    }
}
