package kingmc.util.format

import java.util.*

/**
 * Represent a set of arguments that could be applied to
 * a string to format
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface FormatContext : Iterable<FormatArgument<*>> {
    /**
     * Gets a format argument by name
     *
     * @throws UnsupportedFormatArgumentException if the argument with [name] is not found
     * @return the argument found
     */
    operator fun get(name: String): FormatArgument<*>

    /**
     * Gets a format argument by name
     *
     * @return the argument got, or `null` if the argument with name [name] is not found
     */
    fun getOrNull(name: String): FormatArgument<*>?

    /**
     * Returns a new format context with all arguments hold by this format context and
     * extra arguments from [formatContexts]
     */
    fun with(vararg formatContexts: FormatContext): FormatContext

    /**
     * Returns a new format context with all arguments hold by this format context and
     * extra arguments from [formatContexts]
     */
    fun with(formatContexts: Iterable<FormatContext>): FormatContext

    /**
     * Returns a new format context with the given argument
     */
    fun with(argument: FormatArgument<*>): FormatContext
}

/**
 * A utility superinterface for an object to hold
 * a format context
 *
 * @author kingsthere
 * @since 0.0.3
 */
fun interface FormatContextHolder {
    /**
     * Get the format context that this holder holding
     */
    fun getFormatContext(): FormatContext

}

/**
 * Creates a `FormatContext` with no arguments
 *
 * @return format context created
 */
fun FormatContext(): FormatContext = SimpleFormatContext(
    emptyMap()
)

/**
 * Creates a `FormatContext` for the given list
 *
 * @param list the list of arguments to create format context
 * @return format context created
 */
fun FormatContext(list: List<FormatArgument<*>>): FormatContext = SimpleFormatContext(
    buildMap {
        list.forEach {
            put(it.name, it)
        }
    }
)

/**
 * Creates a `FormatContext` for the given properties instance
 *
 * @param properties the properties to format arguments
 * @return format context created
 */
fun FormatContext(properties: Properties): FormatContext = SimpleFormatContext(
    buildMap {
        properties.forEach { (key, value) -> put(key.toString(), FormatArgument(value, key.toString())) }
    }
)

/**
 * Creates a `FormatContext` for the given map
 *
 * @param map the map to format arguments
 * @return format context created
 */
fun FormatContext(map: Map<String, FormatArgument<*>>): FormatContext = SimpleFormatContext(
    buildMap {
        map.forEach { (key, value) ->
            put(key, value)
        }
    }
)

/**
 * A simple implementation of `FormatContext`
 *
 * @author kingsthere
 * @since 0.1.2
 */
class SimpleFormatContext(
    val formatArguments: Map<String, FormatArgument<*>>,
    val parents: List<FormatContext> = LinkedList()
) : FormatContext {
    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<FormatArgument<*>> {
        return formatArguments.values.iterator()
    }

    /**
     * Gets a format argument by name
     *
     * @throws UnsupportedFormatArgumentException if the argument with [name] is not found
     * @return the argument found
     */
    override fun get(name: String): FormatArgument<*> {
        return formatArguments[name]
            ?: throw UnsupportedFormatArgumentException("Format argument not found with the given name $name")
    }

    /**
     * Gets a format argument by name
     *
     * @return the argument got, or `null` if the argument with name [name] is not found
     */
    override fun getOrNull(name: String): FormatArgument<*>? {
        return formatArguments[name]
    }

    /**
     * Returns a new format context with all arguments hold by this format context and
     * extra arguments from [formatContexts]
     */
    override fun with(vararg formatContexts: FormatContext): FormatContext {
        return SimpleFormatContext(this.formatArguments, this.parents.plus(formatContexts))
    }

    /**
     * Returns a new format context with all arguments hold by this format context and
     * extra arguments from [formatContexts]
     */
    override fun with(formatContexts: Iterable<FormatContext>): FormatContext {
        return SimpleFormatContext(this.formatArguments, this.parents.plus(formatContexts))
    }

    /**
     * Returns a new format context with the given argument
     */
    override fun with(argument: FormatArgument<*>): FormatContext {
        return SimpleFormatContext(this.formatArguments.plus(argument.name to argument), this.parents)
    }
}