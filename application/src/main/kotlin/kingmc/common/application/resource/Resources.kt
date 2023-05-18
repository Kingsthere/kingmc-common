package kingmc.common.application.resource

import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kingmc.common.context.resource.Resource
import kingmc.common.context.resource.ResourceSource
import java.io.File
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.toPath

/**
 * Gets the file of this resource from current application's class loader
 *
 * @return the file got
 */
@WithApplication
fun Resource.getFile(): File {
    val outPath = when (source) {
            ResourceSource.JAR -> Path(value as String)
            ResourceSource.URL -> (value as URL).toURI().toPath()
        }
    return getFile(outPath)
}

/**
 * Gets the file of this resource from current application's class loader
 *
 * @return the file got
 */
@WithApplication
fun Resource.getFile(outPath: Path): File {
    return getFile(outPath, currentApplication().environment.classLoader)
}


