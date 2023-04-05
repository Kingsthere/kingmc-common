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
fun String.formatWithContext(style: FormatStyle = BracketStyle, context: FormatContext): String {
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
fun String.formatWithContext(style: FormatStyle = BracketStyle, context: FormatContextHolder): String {
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
fun String.formatWithContext(style: FormatStyle = BracketStyle, vararg context: FormatContextHolder): String {
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
fun String.formatWithContext(style: FormatStyle = BracketStyle, vararg arguments: Any?): String {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        FormatArgument(argumentIndex, it)
    }
    return formatWithContext(style, ListFormatArguments(formatArguments))
}

/**
 * Format current string by the [FormatStyle] specified
 *
 * @since 0.0.4
 * @author kingsthere
 * @see FormatStyle
 */
fun String.formatWithContext(style: FormatStyle = BracketStyle, formatContext: FormatContext, vararg arguments: Any?): String {
    return formatWithContext(
        style = style,
        context = formatContext.with(
            ListFormatArguments(
                arguments.mapIndexed { index, value ->
                    FormatArgument(
                        index,
                        value,
                    )
                },
            ),
        ),
    )
}

/**
 * Format a string by style from current arguments
 *
 * @since 0.0.4
 * @author kingsthere
 */
fun FormatContext.format(value: String, style: FormatStyle = BracketStyle): String {
    var result = value
    fun formatByMatchResult(matchResult: MatchResult) {
        val formatScopeMatch = matchResult.groups[0]!!
        val formatValueMatch = matchResult.groups[1]
        // Traverse possible format arguments defined in
        // the string and try to handle them
        formatValueMatch?.let {
            // Get the key of the argument found
            val formatScope = formatScopeMatch.value
            val formatValue = formatValueMatch.value
            val indexView = formatValue.toIntOrNull()
            val argument = if (indexView != null) {
                // Try to find the argument by index if possible
                find { it.index == indexView } ?: throw UnsupportedFormatArgumentException("Unsupported format argument index $formatValue")
            } else {
                // Try to find the argument by the name
                find { it.name == formatValue } ?: throw UnsupportedFormatArgumentException("Unsupported format argument $formatValue")
            }

            result = result.replace(formatScope, argument.value.toString())
        }
        matchResult.next()?.let { formatByMatchResult(it) }
    }
    val matchResult = style.find(value)
    matchResult?.let { match ->
        formatByMatchResult(match)
    }
    return result
}

/**
 * Format a string by style from current context's arguments
 *
 * @since 0.0.4
 * @author kingsthere
 */
fun FormatContextHolder.format(value: String, style: FormatStyle = BracketStyle): String {
    var result = value
    fun formatByMatchResult(matchResult: MatchResult) {
        val formatScopeMatch = matchResult.groups[0]!!
        val formatValueMatch = matchResult.groups[1]
        // Traverse possible format arguments defined in
        // the string and try to handle them
        formatValueMatch?.let {
            // Get the key of the argument found
            val formatScope = formatScopeMatch.value
            val formatValue = formatValueMatch.value
            val indexView = formatValue.toIntOrNull()
            val argument = if (indexView != null) {
                // Try to find the argument by index if possible
                getFormatContext().find { it.index == indexView } ?: throw UnsupportedFormatArgumentException("Unsupported format argument index $formatValue")
            } else {
                // Try to find the argument by the name
                getFormatContext().find { it.name == formatValue } ?: throw UnsupportedFormatArgumentException("Unsupported format argument $formatValue")
            }

            result = result.replace(formatScope, argument.value.toString())
        }
        matchResult.next()?.let { formatByMatchResult(it) }
    }
    val matchResult = style.find(value)
    matchResult?.let { match ->
        formatByMatchResult(match)
    }
    return result
}