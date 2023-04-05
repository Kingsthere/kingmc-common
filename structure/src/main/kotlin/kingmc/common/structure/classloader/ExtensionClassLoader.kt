package kingmc.common.structure.classloader

import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

/**
 * A class loader to load the extensions
 *
 * @since 0.0.1
 * @author kingsthere
 */
class ExtensionClassLoader(file: File, parent: ClassLoader) : URLClassLoader(arrayOf(file.toURI().toURL()), parent) {
    fun addToClassloaders() {
        loaders.add(this)
    }

    fun addPath(path: Path) {
        try {
            addURL(path.toUri().toURL())
        } catch (var3: MalformedURLException) {
            throw AssertionError(var3)
        }
    }

    @Throws(IOException::class)
    override fun close() {
        loaders.remove(this)
        super.close()
    }

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String, resolve: Boolean): Class<*> {
        return loadClass0(name, resolve, true)
    }

    @Throws(ClassNotFoundException::class)
    private fun loadClass0(name: String, resolve: Boolean, checkOther: Boolean): Class<*> {
        try {
            return super.loadClass(name, resolve)
        } catch (var8: ClassNotFoundException) {
            if (checkOther) {
                val var4: Iterator<*> = loaders.iterator()
                while (true) {
                    var loader: ExtensionClassLoader
                    do {
                        if (!var4.hasNext()) {
                            throw ClassNotFoundException(name)
                        }
                        loader = var4.next() as ExtensionClassLoader
                    } while (loader === this)
                    try {
                        return loader.loadClass0(name, resolve, false)
                    } catch (_: ClassNotFoundException) {
                    }
                }
            } else {
                throw ClassNotFoundException(name)
            }
        }
    }

    override fun getResource(name: String?): URL? {
        return findResource(name)
    }

    companion object {
        private val loaders: MutableSet<ExtensionClassLoader?> = CopyOnWriteArraySet<ExtensionClassLoader?>()

        init {
            registerAsParallelCapable()
        }
    }
}