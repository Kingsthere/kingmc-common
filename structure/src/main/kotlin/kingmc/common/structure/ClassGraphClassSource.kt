package kingmc.common.structure

import io.github.classgraph.ClassGraph

/**
 * Implemented `ClassSource` by load classes from specified jar [file]
 *
 * @since 0.0.1
 * @author kingsthere
 */
open class ClassGraphClassSource : ClassSource {
    protected val classGraph: ClassGraph = ClassGraph()
        .enableAnnotationInfo()
        .enableClassInfo()

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
        return buildList {
            classGraph.scan().allClasses.forEach { classInfo ->
                try {
                    val loadedClass = classInfo.loadClass()
                    this@ClassGraphClassSource.whenLoadClass(loadedClass)
                    add(loadedClass)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
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