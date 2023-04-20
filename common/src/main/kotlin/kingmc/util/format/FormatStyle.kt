package kingmc.util.format

import kingmc.util.SubclassSingleton

/**
 * The style of a format to format a text, for example:
 * style is `{ value }`, then you can use `{ 0 }` to format
 * the string
 *
 * @since 0.0.4
 * @author kingsthere
 */
@SubclassSingleton
interface FormatStyle {
    /**
     * Convert this style as a [regex] that this format style used to match
     * elements in text to format, `group 1` for this regex must match the
     * key to format
     */
    val regex: Regex

    /**
     * Find the formats that defined in specified string
     *
     * @since 0.0.4
     */
    fun find(value: CharSequence): MatchResult?

    /**
     * Check if any format is defined in down string
     *
     * @since 0.0.4
     */
    fun matches(value: CharSequence): Boolean =
        this.find(value) != null
}