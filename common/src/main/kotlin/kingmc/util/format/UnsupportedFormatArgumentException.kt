package kingmc.util.format

/**
 * An exception thrown when trying to format a string with
 * argument that not supported from [FormatContext]
 *
 * @since 0.0.3
 * @author kingsthere
 */
class UnsupportedFormatArgumentException(message: String?) : RuntimeException(message)