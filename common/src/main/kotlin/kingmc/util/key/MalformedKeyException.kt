package kingmc.util.key

/**
 * This exception is thrown when trying to cast a invalid string to [Key] instance
 *
 * @since 0.0.1
 */
class MalformedKeyException(
    val string: String
) : RuntimeException("Invalid key \"$string\"")