package kingmc.util.format

import kingmc.common.text.Text

/**
 * Format the receiver by the [FormatStyle] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param context the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun String.format(style: FormatStyle = BracketStyle, context: FormatContext): String {
    return context.format(this, style)
}

/**
 * Format the receiver by the [FormatStyle] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param context the context holder held the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun String.format(style: FormatStyle = BracketStyle, context: FormatContextHolder): String {
    return context.getFormatContext().format(this, style)
}

/**
 * Format the receiver by multiple [FormatStyle] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param contexts context holders held the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun String.format(style: FormatStyle = BracketStyle, vararg contexts: FormatContextHolder): String {
    var newValue: String = this
    for (formatContextHolder in contexts) {
        newValue = formatContextHolder.getFormatContext().format(newValue, style)
    }
    return newValue
}

/**
 * Format the receiver by the [FormatStyle] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param arguments extra arguments to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun String.format(style: FormatStyle = BracketStyle, vararg arguments: Any?): String {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        FormatArgument(argumentIndex, it)
    }
    return this@format.format(style, ListFormatArguments(formatArguments))
}

/**
 * Format the receiver by the [FormatStyle] specified
 *
 * @receiver the text to format
 * @param style the style to format the string
 * @param context the context to format the string
 * @return formatted text
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun Text.format(style: FormatStyle = BracketStyle, context: FormatContext): Text {
    return context.format(this, style)
}

/**
 * Format the receiver by the [FormatStyle] specified
 *
 * @receiver the text to format
 * @param style the style to format the string
 * @param context the context holder held the context to format the string
 * @receiver formatted text
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun Text.format(style: FormatStyle = BracketStyle, context: FormatContextHolder): Text {
    return context.getFormatContext().format(this, style)
}

/**
 * Format the receiver by multiple [FormatStyle] specified
 *
 * @receiver the text to format
 * @param style the style to format the string
 * @param contexts contexts holders held the context to format the string
 * @return formatted text
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun Text.format(style: FormatStyle = BracketStyle, vararg contexts: FormatContextHolder): Text {
    var newValue: Text = this
    for (formatContextHolder in contexts) {
        newValue = formatContextHolder.getFormatContext().format(newValue, style)
    }
    return newValue
}

/**
 * Format the receiver by the [FormatStyle] specified
 *
 * @param style the style to format the text
 * @param arguments extra arguments to format
 * @return formatted text
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun Text.format(style: FormatStyle = BracketStyle, vararg arguments: Any?): Text {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        FormatArgument(argumentIndex, it)
    }
    return this@format.format(style, ListFormatArguments(formatArguments))
}

/**
 * Format the receiver by the [FormatStyle] and extra [arguments] specified
 *
 * @receiver the string to format
 * @param style the style to format
 * @param formatContext the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun String.format(style: FormatStyle = BracketStyle, formatContext: FormatContext, vararg arguments: Any?): String {
    return this@format.format(
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
 * Format the receiver by the [FormatStyle] and extra [arguments] specified
 *
 * @receiver the text to format
 * @param style the style to format
 * @param formatContext the context to format the text
 * @return formatted text
 * @since 0.0.9
 * @author kingsthere
 * @see FormatStyle
 */
fun Text.format(style: FormatStyle = BracketStyle, formatContext: FormatContext, vararg arguments: Any?): Text {
    return this@format.format(
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
 * @since 0.0.9
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
                find { it.index == indexView }
            } else {
                // Try to find the argument by the name
                find { it.name == formatValue }
            }

            if (argument != null) {
                result = result.replace(formatScope, argument.value.toString())
            }
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
 * Format a `Text` by style from current arguments
 *
 * @since 0.0.9
 * @author kingsthere
 */
fun FormatContext.format(value: Text, style: FormatStyle = BracketStyle): Text {
    return value.replaceText { replacement ->
        replacement.match(style.regex.toPattern()).replacement { match, result ->
            val formatValue = match.group(1)
            val indexView = formatValue.toIntOrNull()
            val argument = if (indexView != null) {
                // Try to find the argument by index if possible
                find { it.index == indexView }
            } else {
                // Try to find the argument by the name
                find { it.name == formatValue }
            }
            if (argument != null) {
                result.content(argument.value.toString())
            } else {
                result.content(match.group())
            }
        }
    }
}

/**
 * Format a string by style from current context's arguments
 *
 * @since 0.0.9
 * @author kingsthere
 */
fun FormatContextHolder.format(value: String, style: FormatStyle = BracketStyle): String =
    getFormatContext().format(value, style)