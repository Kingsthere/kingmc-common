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
     * extra arguments from [formatContext]
     */
    fun with(formatContext: FormatContext): FormatContext
}

/**
 * A abstract [FormatContext] format implementation
 *
 * @since 0.0.6
 * @author kingsthere
 */
open class AbstractFormatContext(val parent: FormatContext? = null) : FormatContext {
    /**
     * Gets a format argument by name
     *
     * @throws UnsupportedFormatArgumentException if the argument with [name] is not found
     * @return the argument found
     */
    override fun get(name: String): FormatArgument<*> {
        return getImplemented(name) ?: parent?.get(name) ?: throw UnsupportedFormatArgumentException("Argument with name $name is not found")
    }

    /**
     * Gets a format argument by name
     *
     * @return the argument got, or `null` if the argument with name [name] is not found
     */
    override fun getOrNull(name: String): FormatArgument<*>? {
        return getImplemented(name) ?: parent?.getOrNull(name)
    }

    /**
     * Returns a new format context with all arguments hold by this format context and
     * extra arguments from [formatContext]
     */
    override fun with(formatContext: FormatContext): FormatContext {
        return AbstractFormatContext(formatContext)
    }

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<FormatArgument<*>> {
        throw UnsupportedOperationException()
    }

    open fun getImplemented(name: String): FormatArgument<*>? { return null }

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
open class ListFormatArguments(open val arguments: List<FormatArgument<*>> = listOf(), parent: FormatContext? = null) :
    AbstractFormatContext(parent) {

    override fun getImplemented(name: String): FormatArgument<*>? {
        return arguments.find { it.name == name }
    }

    /**
     * Returns a new format context with all arguments hold by this format context and
     * extra arguments from [formatContext]
     */
    override fun with(formatContext: FormatContext): FormatContext {
        return ListFormatArguments(arguments, formatContext)
    }

    override fun iterator(): Iterator<FormatArgument<*>> {
        return arguments.iterator()
    }
}