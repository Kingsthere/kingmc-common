package kingmc.util.format

/**
 * Format current string by the [FormatStyle] specified
 *
 * @since 0.0.4
 * @author kingsthere
 * @param style the style to format the string
 * @param context the context to format the string
 * @see FormatStyle
 */
fun String.formatByStyle(style: FormatStyle = BracketStyle, context: FormatContext): String {
    return context.format(this, style)
}

/**
 * Format current string by the [FormatStyle] specified
 *
 * @since 0.0.4
 * @author kingsthere
 * @param style the style to format the string
 * @param context the context holder held the context to format the string
 * @see FormatStyle
 */
fun String.formatByStyle(style: FormatStyle = BracketStyle, context: FormatContextHolder): String {
    return context.getFormatContext().format(this, style)
}

/**
 * Format current string by the [FormatStyle] specified
 *
 * @since 0.0.4
 * @author kingsthere
 * @param style the style to format the string
 * @param context the context holders held the context to format the string
 * @see FormatStyle
 */
fun String.formatByStyle(style: FormatStyle = BracketStyle, vararg context: FormatContextHolder): String {
    var newValue: String = this
    for (formatContextHolder in context) {
        newValue = formatContextHolder.getFormatContext().format(newValue, style)
    }
    return newValue
}

/**
 * Format current string by the [FormatStyle] specified
 *
 * @since 0.0.4
 * @author kingsthere
 * @see FormatStyle
 */
fun String.formatByStyle(style: FormatStyle = BracketStyle, vararg arguments: Any?): String {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        formatArgument(argumentIndex, it)
    }
    return formatByStyle(style, DefaultFormatArguments(formatArguments))
}

/**
 * Format a string by style from current arguments
 *
 * @since 0.0.4
 * @author kingsthere
 */
fun FormatContext.format(value: String, style: FormatStyle): String {
    var result = value
    val matchResult = style.find(value)
    matchResult?.let { match ->
        match.groups.forEach { group ->
            // Traverse possible format arguments defined in
            // the string and try to handle them
            group?.let {
                // Get the key of the argument found
                val key = group.value
                // Try to find the argument by the name
                val argument = find { it.name == key } ?:
                    throw UnsupportedFormatArgumentException("Unsupported format argument $key")
                result = result.replace(key, argument.value.toString())
            }
        }
    }
    return result
}