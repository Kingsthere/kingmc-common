package kingmc.util.format

/**
 * Represent a single format argument
 *
 * @author kingsthere
 * @since 0.0.3
 */
sealed interface FormatArgument<E> {
    /**
     * The name of this format argument
     */
    val name: String

    /**
     * The value of this format argument
     */
    val value: E
}

/**
 * Create and return a format argument implementation
 */
fun <E> FormatArgument(value: E, name: String) =
    FormatArgumentImpl(value, name)

class FormatArgumentImpl<E>(override val value: E, override val name: String) : FormatArgument<E>