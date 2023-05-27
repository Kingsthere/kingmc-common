package kingmc.util.format

import kingmc.common.text.Text

/**
 * Format the receiver by the [Formatter] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param context the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, context: FormatContext): String {
    return context.formatWithContext(this, formatter)
}

/**
 * Format the receiver by the [Formatter] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param context the context holder held the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, context: FormatContextHolder): String {
    return context.getFormatContext().formatWithContext(this, formatter)
}

/**
 * Format the receiver by multiple [Formatter] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param contexts context holders held the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, vararg contexts: FormatContextHolder): String {
    var newValue: String = this
    for (formatContextHolder in contexts) {
        newValue = formatContextHolder.getFormatContext().formatWithContext(newValue, formatter)
    }
    return newValue
}

/**
 * Format the receiver by the [Formatter] specified
 *
 * @receiver the string to format
 * @param style the style to format the string
 * @param arguments extra arguments to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, vararg arguments: Any?): String {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        FormatArgument(it, argumentIndex.toString())
    }
    return this@formatWithContext.formatWithContext(formatter, ListFormatArguments(formatArguments))
}

/**
 * Format the receiver by the [Formatter] specified
 *
 * @param style the style to format the text
 * @param arguments extra arguments to format
 * @return formatted text
 * @since 0.0.9
 * @author kingsthere
 * @see Formatter
 */
fun Text.formatWithContext(formatter: Formatter = BracketStyle, vararg arguments: Any?): Text {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        FormatArgument(it, argumentIndex.toString())
    }
    return this@formatWithContext.formatWithContext(formatter, ListFormatArguments(formatArguments))
}

/**
 * Format the receiver by the [Formatter] and extra [arguments] specified
 *
 * @receiver the string to format
 * @param style the style to format
 * @param formatContext the context to format the string
 * @return formatted string
 * @since 0.0.9
 * @author kingsthere
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, formatContext: FormatContext, vararg arguments: Any?): String {
    return this@formatWithContext.formatWithContext(
        formatter = formatter,
        context = formatContext.with(
            ListFormatArguments(
                arguments.mapIndexed { index, value ->
                    FormatArgument(
                        value,
                        index.toString()
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
fun FormatContext.formatWithContext(value: String, formatter: Formatter = BracketStyle): String {
    return formatter.format(value, this)
}

/**
 * Format a string by style from current context's arguments
 *
 * @since 0.0.9
 * @author kingsthere
 */
fun FormatContextHolder.formatWithContext(value: String, formatter: Formatter = BracketStyle): String =
    getFormatContext().formatWithContext(value, formatter)