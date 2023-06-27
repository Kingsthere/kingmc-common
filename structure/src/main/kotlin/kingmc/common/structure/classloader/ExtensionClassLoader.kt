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
class ExtensionClassLoader(private val _file: File, private val _parent: ClassLoader) : URLClassLoader(arrayOf(_file.toURI().toURL()), _parent) {
    private val dependencyClassLoaders: MutableList<ExtensionClassLoader> = ArrayList()

    fun addToClassloaders() {
        loaders.add(this)
    }

    fun addDependency(extensionClassLoader: ExtensionClassLoader) {
        dependencyClassLoaders.add(extensionClassLoader)
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
                val classLoaders: Iterator<*> = (loaders + dependencyClassLoaders).iterator()
                while (true) {
                    var loader: ExtensionClassLoader
                    do {
                        if (!classLoaders.hasNext()) {
                            throw ClassNotFoundException(name)
                        }
                        loader = classLoaders.next() as ExtensionClassLoader
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

    override fun toString(): String {
        return "ExtensionClassLoader(file=$_file, parent=$_parent)"
    }

    companion object {
        private val loaders: MutableSet<ExtensionClassLoader?> = CopyOnWriteArraySet<ExtensionClassLoader?>()

        init {
             // registerAsParallelCapable()
        }
    }
}