package kingmc.util

/**
 * A marker interface to mark the classes that is [Reloadable], Reloadable
 * is a thing that could [reload], when you reload this thing it re-load
 * resources such as configs, database connections...
 *
 * @author kingsthere
 * @since 0.0.1
 */
interface Reloadable {
    /**
     * Reload
     */
    fun reload()
}