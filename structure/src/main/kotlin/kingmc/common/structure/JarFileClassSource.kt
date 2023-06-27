package kingmc.common.structure

import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Implemented `ClassSource` by load classes from specified jar [file]
 *
 * @since 0.0.1
 * @author kingsthere
 */
open class JarFileClassSource(val file: File, var classLoader: ClassLoader) : ClassSource {

    /**
     * Detected class names from [file]
     */
    private val classNames: MutableList<String> = mutableListOf()

    /**
     * The loaded classes from [file]
     *
     * @see getClasses
     */
    private lateinit var _classes : List<Class<*>>

    fun load() {
        _classes = loadClasses()
    }

    fun loadClasses(): List<Class<*>> {
        if (classNames.isNotEmpty()) {
            classNames.clear()
        }

        val zip = ZipInputStream(FileInputStream(file))
        var entry: ZipEntry? = zip.nextEntry
        while (entry != null) {
            if (!entry.isDirectory && entry.name.endsWith(".class")) {
                // This ZipEntry represents a class. Now, what class does it represent?
                val classFileName = entry.name.replace('/', '.') // including ".class"
                this.classNames.add(classFileName.substring(0, classFileName.length - ".class".length))
            }
            entry = zip.nextEntry
        }
        zip.close()
        return loadClassByNames()
    }

    fun loadClassByNames(): List<Class<*>> {
        return classNames.mapNotNull {
            try {
                val classLoaded = classLoader.loadClass(it)
                this.whenLoadClass(classLoaded)
                return@mapNotNull classLoaded
            } catch (e: NoClassDefFoundError) {
                return@mapNotNull null
            } catch (e: Exception) {
                throw ProjectInitializeException("Unable to load project", e)
            }
        }
    }

    override fun reload() {
        _classes = loadClasses()
    }

    override fun getClasses(): List<Class<*>> {
        // Return the class supplier supplied
        return _classes
    }

    protected open fun whenLoadClass(clazz: Class<*>) {  }

    @ExperimentalStructureApi
    override fun getPluggable(): Set<Pluggable> {
        TODO("Not yet implemented")
    }
}