package kingmc.common.structure

import kingmc.util.Reloadable
import java.util.function.Predicate
import java.util.stream.Collectors

/**
 * Indicate a source provide classes
 *
 * @see Class
 * @since 0.0.1
 * @author kingsthere
 */
interface ClassSource : Reloadable {
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