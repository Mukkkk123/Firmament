
package moe.nea.notfirmament.util

import java.io.InputStream
import kotlin.io.path.inputStream
import kotlin.jvm.optionals.getOrNull
import net.minecraft.resources.Identifier
import moe.nea.notfirmament.repo.RepoDownloadManager


fun Identifier.openNotFirmamentResource(): InputStream {
    val resource = MC.resourceManager.getResource(this).getOrNull()
    if (resource == null) {
        if (namespace == "neurepo")
            return RepoDownloadManager.repoSavedLocation.resolve(path).inputStream()
        error("Could not read resource $this")
    }
    return resource.open()
}

