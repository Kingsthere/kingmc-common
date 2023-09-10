package kingmc.util

import java.nio.file.Path

/**
 * Interface for url classloaders exposed a function to append external path to classpath
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface AppendableURLClassLoader {
    /**
     * Add the given path to classpath to this classloader
     */
    fun addPath(path: Path)
}