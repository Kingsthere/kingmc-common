package kingmc.util.format

/**
 * Represent a set of arguments that could be applied to
 * a string to format
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface FormatContext : Iterable<FormatArgument<*>> {
    /**
     * Gets a format argument by index
     *
     * @throws UnsupportedFormatArgumentException if the argument with [index] is not found
     * @return the argument found
     */
    operator fun get(index: Int): FormatArgument<*>

    /**
     * Gets a format argument by name
     *
     * @throws UnsupportedFormatArgumentException if the argument with [name] is not found
     * @return the argument found
     */
    operator fun get(name: String): FormatArgument<*>

    /**
     * Gets a format argument by index
     *
     * @return the argument got, or `null` if this argument with specifies [index] is not found
     */
    fun getOrNull(index: Int): FormatArgument<*>?

    /**
     * Gets a format argument by name
     *
     * @return the argument got, or `null` if the argument with name [name] is not found
     */
    fun getOrNull(name: String): FormatArgument<*>?
}

/**
 * A utility superinterface for an object to hold
 * a format context
 *
 * @since 0.0.3
 * @author kingsthere
 */
fun interface FormatContextHolder {
    /**
     * Get the format context that this holder holding
     */
    fun getFormatContext(): FormatContext
}

/**
 * Represent a mutable set of arguments that could be applied to
 * a string to format
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface MutableFormatContext : FormatContext, MutableList<FormatArgument<*>>

/**
 * An implementation of [FormatContext] provide format arguments by a [list]
 *
 * @since 0.0.4
 * @author kingsthere
 */
open class ListFormatArguments(val arguments: List<FormatArgument<*>> = listOf()) :
    FormatContext{
    override fun get(index: Int): FormatArgument<*> {
        return arguments.find { it.index == index } ?: throw UnsupportedFormatArgumentException("Argument with index $index is not found")
    }

    override fun get(name: String): FormatArgument<*> {
        return arguments.find { it.name == name } ?: throw UnsupportedFormatArgumentException("Argument with name $name is not found")
    }

    override fun getOrNull(index: Int): FormatArgument<*>? {
        return arguments.find { it.index == index }
    }

    override fun getOrNull(name: String): FormatArgument<*>? {
        return arguments.find { it.name == name }
    }

    override fun iterator(): Iterator<FormatArgument<*>> {
        return arguments.iterator()
    }
}