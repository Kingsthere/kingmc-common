package kingmc.util.format

/**
 * Represent a set of arguments that could be applied to
 * a string to format
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface FormatContext : List<FormatArgument<*>>, Iterable<FormatArgument<*>>

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
 * A default implementation of [FormatContext]
 *
 * @since 0.0.4
 * @author kingsthere
 */
class DefaultFormatArguments(arguments: List<FormatArgument<*>> = listOf()) :
    FormatContext,
    List<FormatArgument<*>> by arguments