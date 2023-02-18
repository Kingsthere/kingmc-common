package kingmc.util.format

/**
 * Format style, use `{}` to match formats, for
 * example: { 0 } {1} {test}
 *
 * @since 0.0.3
 * @see FormatStyle
 */
object BracketStyle : FormatStyle {
    private val regex = "(\\{\\s+?(\\S*)\\s+?})".toRegex()

    /**
     * Find the formats that defined in specified string
     *
     * @since 0.0.4
     */
    override fun find(value: CharSequence): MatchResult? =
        regex.find(value)

    override fun matches(value: CharSequence): Boolean =
        regex.matches(value)
}
