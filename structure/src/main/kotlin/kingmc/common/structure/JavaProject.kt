package kingmc.common.structure

import java.io.File

/**
 * Project to get classes from class loader
 *
 * @since 0.0.3
 * @author kingsthere
 */
open class JavaProject(
    val classLoader: ClassLoader,
    private val packageInfo: PackageInfo?,
) : Project {
    /**
     * Get a class from this project
     *
     * @return the project got, `null` if the
     *         class is not define in this project
     * @since 0.0.2
     * @author kingsthere
     */
    override fun <T : Any> getClass(name: String): Class<T>? {
        TODO()
    }

    /**
     * Invoke this function to get all classes
     * in this project as a [List]
     *
     * @see Class
     * @see List
     * @since 0.0.2
     * @author kingsthere
     * @throws ProjectInitializeException when trying to initialize this project to
     *                                    get all classes in this project
     */
    override fun getClasses(): List<Class<*>> {
        val classes: MutableList<Class<*>> = mutableListOf()
        if (packageInfo == null) {
            val resources = classLoader.getResources("")
            for (resource in resources) {
                if (resource.protocol == "file") {
                    this.loadClassByPath(null, resource.path, classLoader, classes)
                }
            }
        } else {
            packageInfo.included.forEach { include ->
                val resources = classLoader.getResources(include.replace('.', '/'))
                for (resource in resources) {
                    if (resource.protocol == "file") {
                        this.loadClassByPath(null, resource.path, classLoader, classes) { include.contains(it) }
                    }
                }
            }
        }
        return classes
    }

    /**
     * Check if the specified class is defined in this project
     *
     * @since 0.0.2
     * @author kingsthere
     */
    override fun contains(clazz: Class<*>): Boolean {
        return getClasses().contains(clazz)
    }

    /**
     * Load classes from a url path and add to the specified
     * list
     */
    private fun loadClassByPath(root: String?,
                                path: String,
                                classLoader:
                                ClassLoader,
                                classes: MutableList<Class<*>>,
                                exclude: ((String) -> Boolean)? = null) {
        val file = File(path)
        val newRoot = root ?: file.path
        // Check if the resource is a .class file
        if (file.isFile && file.name.endsWith(".class")) {
            val classPath = file.path
            // Split the class name from the file path
            // Example: com/kingmc/Test.class -> net.kingmc.Test
            val className = classPath
                .substring(newRoot.length + 1, classPath.length - 6)
                .replace('/', '.')
                .replace('\\', '.')


            // Check if classname is passed the filter
            if (exclude != null && !exclude.invoke(className)) {
                return
            }

            try {
                classes.add(
                    classLoader.loadClass(className)
                )
            } catch (e: Exception) {
                throw ProjectInitializeException("Unable to load project class $path", e)
            }
        } else {
            val files = file.listFiles() ?: return
            files.forEach { loadClassByPath(newRoot, it.path, classLoader, classes) }
        }
    }

    /**
     * Invoke this function to get all pluggable
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