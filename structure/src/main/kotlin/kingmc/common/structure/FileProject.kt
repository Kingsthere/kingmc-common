package kingmc.common.structure

import kingmc.common.structure.annotation.PackageScan
import java.io.File
import java.io.FileInputStream
import java.util.function.Supplier
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Implement of [Project] represent a project that
 * load from a jar file
 *
 * @since 0.0.1
 * @author kingsthere
 */
open class FileProject
    (val file: File,
     var classLoader: ClassLoader?) : Project {

    /**
     * The name of all classes in this project
     *
     * @since 0.0.1
     */
    private var classNames: MutableList<String>
    /**
     * Package information to this project about
     * the excluded/included packages
     *
     * @since 0.0.1
     */
    private lateinit var packageInfo: PackageInfo

    /**
     * The supplier to supply the classes in this project
     *
     * @see getClasses
     */
    var classes : Supplier<List<Class<*>>>

    init {
        this.classNames = ArrayList()
        val zip = ZipInputStream(FileInputStream(file))
        var entry: ZipEntry? = zip.nextEntry
        while (entry != null) {
            if (!entry.isDirectory && entry.name.endsWith(".class")) {
                // This ZipEntry represents a class. Now, what class does it represent?
                val className = entry.name.replace('/', '.') // including ".class"

                var isExclude = false
                for (excluded in PackageInfo.DEFAULT_EXCLUDED) {
                    if (className.startsWith(excluded)) {
                        isExclude = true
                        break
                    }
                }
                if (!isExclude) {
                    this.classNames.add(className.substring(0, className.length - ".class".length))
                }
            }
            entry = zip.nextEntry
        }
        zip.close()
        this.classes = Supplier <List<Class<*>
                /**
                 * Gets a result.
                 *
                 * @return a result
                 */
                >> {
            val value: MutableList<Class<*>> = ArrayList()
            if (classLoader != null) {

                for (className in classNames) {
                    try {
                        val clazz: Class<*> = classLoader!!.loadClass(className) ?: continue
                        clazz.sourceFile = this.file

                        if (clazz.isAnnotationPresent(PackageScan::class.java)) {
                            val annotation: PackageScan = clazz.getAnnotation(PackageScan::class.java)
                            packageInfo = PackageInfo.create(
                                annotation.excluded.toSet(),
                                annotation.included.toSet()
                            )
                        }
                        value.add(clazz)
                    } catch (e: Exception) {
                        throw ProjectInitializeException("Unable to load project", e)
                    } catch (e: NoClassDefFoundError) {
                        // Send warning log?
                    }

                }
            } else {
                for (className in classNames) {
                    try {
                        val clazz: Class<*> = Class.forName(className) ?: continue

                        if (clazz.isAnnotationPresent(PackageScan::class.java)) {
                            val annotation: PackageScan = clazz.getAnnotation(PackageScan::class.java)
                            packageInfo = PackageInfo.create(
                                annotation.excluded.toSet(),
                                annotation.included.toSet()
                            )
                        }
                        value.add(clazz)
                    } catch (e: Exception) {
                        throw ProjectInitializeException("Unable to load project", e)
                    }

                }
            }
            this@FileProject.filterClasses(value)
        }
    }

    fun reload() {

        this.classNames = ArrayList()
        val zip = ZipInputStream(FileInputStream(file))
        var entry: ZipEntry? = zip.nextEntry
        while (entry != null) {
            if (!entry.isDirectory && entry.name.endsWith(".class")) {
                // This ZipEntry represents a class. Now, what class does it represent?
                val className = entry.name.replace('/', '.') // including ".class"

                var isExclude = false
                for (excluded in PackageInfo.DEFAULT_EXCLUDED) {
                    if (className.startsWith(excluded)) {
                        isExclude = true
                        break
                    }
                }
                if (!isExclude) {
                    this.classNames.add(className.substring(0, className.length - ".class".length))
                }
            }
            entry = zip.nextEntry
        }
        zip.close()
        this.classes = Supplier <List<Class<*>
                /**
                 * Gets a result.
                 *
                 * @return a result
                 */
                >> {
            val value: MutableList<Class<*>> = ArrayList()
            if (classLoader != null) {

                for (className in classNames) {
                    try {

                        val clazz: Class<*> = classLoader!!.loadClass(className) ?: continue

                        if (clazz.isAnnotationPresent(PackageScan::class.java)) {
                            val annotation: PackageScan = clazz.getAnnotation(PackageScan::class.java)
                            packageInfo = PackageInfo.create(
                                annotation.excluded.toSet(),
                                annotation.included.toSet()
                            )
                        }
                        value.add(clazz)
                    } catch (e: Exception) {
                        throw ProjectInitializeException("Unable to load project", e)
                    }

                }
            } else {
                for (className in classNames) {
                    try {

                        val clazz: Class<*> = Class.forName(className) ?: continue

                        if (clazz.isAnnotationPresent(PackageScan::class.java)) {
                            val annotation: PackageScan = clazz.getAnnotation(PackageScan::class.java)
                            packageInfo = PackageInfo.create(
                                annotation.excluded.toSet(),
                                annotation.included.toSet()
                            )
                        }
                        value.add(clazz)
                    } catch (e: Exception) {
                        throw ProjectInitializeException("Unable to load project", e)
                    }

                }
            }
            this@FileProject.filterClasses(value)
        }
    }

    /**
     * Filter the classes input by the [PackageInfo]
     *
     * @since 0.0.1
     */
    private fun filterClasses(classes: List<Class<*>>) : List<Class<*>> {
        val list : MutableList<Class<*>> = ArrayList()
        for (clazz in classes) {
            a@
            for (include : String in packageInfo.included) {
                try {
                    if (clazz.name.startsWith(include)) {
                        list.add(clazz)
                        break@a
                    }
                } catch (_: IncompatibleClassChangeError) {
                    break@a
                }
            }
        }
        return list
    }

    /**
     * Get a class from this project
     *
     * @return the project got, `null` if the
     *         class is not define in this project
     * @since 0.0.2
     * @author kingsthere
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getClass(name: String): Class<T>? =
        (this.classLoader?.loadClass(name) ?: Class.forName(name))!! as Class<T>

    override fun getClasses(): List<Class<*>> {
        // Return the class supplier supplied
        return classes.get()
    }

    override fun contains(clazz: Class<*>): Boolean {
        return this.getClasses().contains(clazz)
    }

    /**
     * Invoke this function to get all pluggable features
     * in this project as a [Set]
     *
     * @see Set
     * @see Pluggable
     * @since 0.0.1
     */
    @ExperimentalStructureApi
    override fun getPluggable(): Set<Pluggable> {
        TODO("Not yet implemented")
    }
}