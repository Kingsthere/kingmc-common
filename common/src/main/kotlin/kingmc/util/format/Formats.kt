package kingmc.util.format

/**
 * Format the receiver by the [Formatter] specified
 *
 * @receiver the string to format
 * @param formatter the formatter to format the string
 * @param context the context to format the string
 * @return formatted string
 * @author kingsthere
 * @since 0.0.9
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, context: FormatContext): String {
    return context.formatWithContext(this, formatter)
}

/**
 * Format the receiver by the [Formatter] specified
 *
 * @receiver the string to format
 * @param formatter the formatter to format the string
 * @param context the context holder held the context to format the string
 * @return formatted string
 * @author kingsthere
 * @since 0.0.9
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, context: FormatContextHolder): String {
    return context.getFormatContext().formatWithContext(this, formatter)
}

/**
 * Format the receiver by multiple [Formatter] specified
 *
 * @receiver the string to format
 * @param formatter the formatter to format the string
 * @param contexts context holders held the context to format the string
 * @return formatted string
 * @author kingsthere
 * @since 0.0.9
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
 * @param arguments extra arguments to format the string
 * @return formatted string
 * @author kingsthere
 * @since 0.0.9
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, vararg arguments: Any?): String {
    val argumentIndex = 0
    val formatArguments: List<FormatArgument<*>> = arguments.map {
        FormatArgument(it, argumentIndex.toString())
    }
    return this@formatWithContext.formatWithContext(formatter, FormatContext(formatArguments))
}

/**
 * Format the receiver by the [Formatter] and extra [arguments] specified
 *
 * @receiver the string to format
 * @param formatter the formatter to format the string
 * @param formatContext the context to format the string
 * @return formatted string
 * @author kingsthere
 * @since 0.0.9
 * @see Formatter
 */
fun String.formatWithContext(formatter: Formatter = BracketStyle, formatContext: FormatContext, vararg arguments: Any?): String {
    return this@formatWithContext.formatWithContext(
        formatter = formatter,
        context = formatContext.with(
            FormatContext(
                arguments.mapIndexed { index, argument -> FormatArgument(argument, index.toString()) }
            ),
        ),
    )
}

/**
 * Format a string by style from current arguments
 *
 * @author kingsthere
 * @since 0.0.9
 */
fun FormatContext.formatWithContext(value: String, formatter: Formatter = BracketStyle): String {
    return formatter.format(value, this)
}

/**
 * Format a string by style from current context's arguments
 *
 * @author kingsthere
 * @since 0.0.9
 */
fun FormatContextHolder.formatWithContext(value: String, formatter: Formatter = BracketStyle): String =
    getFormatContext().formatWithContext(value, formatter)