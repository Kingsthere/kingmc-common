package kingmc.util.format

/**
 * Represent a single format argument
 *
 * @since 0.0.3
 * @author kingsthere
 */
sealed interface FormatArgument<E> {
    /**
     * The index of this format argument
     */
    val index: Int

    /**
     * The name of this format argument
     */
    val name: String?

    /**
     * The value of this format argument
     */
    val value: E
}

/**
 * Create and return a format argument implementation
 */
fun <E> FormatArgument(index: Int, value: E, name: String? = null) =
    FormatArgumentImpl(index, value, name)

class FormatArgumentImpl<E>(override val index: Int, override val value: E, override val name: String? = null) : FormatArgument<E>