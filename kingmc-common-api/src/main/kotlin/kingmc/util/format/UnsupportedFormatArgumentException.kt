package kingmc.util.format

/**
 * An exception thrown when trying to format a string with
 * argument that not supported from [FormatContext]
 *
 * @author kingsthere
 * @since 0.0.3
 */
class UnsupportedFormatArgumentException(message: String?) : RuntimeException(message)