package kingmc.common.structure

import java.util.function.Predicate
import java.util.stream.Collectors

/**
 * A project is a set of sources for defining classes...
 *
 *
 * The classes in the project stores should only contain
 * the unique code in your own project, not the dependencies
 * or shadows
 *
 * @see Class
 * @since 0.0.1
 * @author kingsthere
 */
interface Project {
    /**
     * Gets a class from this project
     *
     * @return the project got, `null` if the
     *         class is not define in this project
     * @since 0.0.2
     * @author kingsthere
     */
    fun <T : Any> getClass(name: String): Class<T>?

    /**
     * Gets all classes in this project as a [List]
     *
     * @see Class
     * @see List
     * @since 0.0.2
     * @author kingsthere
     * @throws ProjectInitializeException when trying to initialize this project to
     *                                    get all classes in this project
     */
    @Throws(ProjectInitializeException::class)
    fun getClasses() : List<Class<*>>

    /**
     * Gets filtered classes in this project and return them as a [List]
     *
     * @see Predicate
     * @see Class
     * @see List
     * @since 0.0.2
     * @author kingsthere
     * @throws ProjectInitializeException when trying to initialize this project to
     *                                    get all classes in this project
     */
    @Throws(ProjectInitializeException::class)
    fun getClasses(predicate: Predicate<Class<*>>) : List<Class<*>> {
        // Delegate to function getClasses() and filter the
        // results by stream api using the predicate specified
        return this.getClasses().stream().filter(predicate).collect(Collectors.toList())
    }

    /**
     * Check if the specifies class is defined in this project
     *
     * @since 0.0.2
     * @author kingsthere
     */
    operator fun contains(clazz: Class<*>): Boolean

    /**
     * Invoke this function to get all pluggable
     * in this project as a [Set]
     *
     * @see Set
     * @see Pluggable
     * @since 0.0.1
     */
    @ExperimentalStructureApi
    fun getPluggable() : Set<Pluggable>
}