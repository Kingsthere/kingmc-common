package kingmc.util.format

import kingmc.util.SubclassSingleton

/**
 * The style of a format to format a string, for example:
 * style is `{ value }`, then you can use `{ 0 }` to format
 * the string
 *
 * @since 0.0.4
 * @author kingsthere
 */
@SubclassSingleton
fun interface FormatStyle {
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