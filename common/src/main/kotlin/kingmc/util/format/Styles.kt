package kingmc.util.format

/**
 * A Format style uses `{}` to match formats, for
 * example: { 0 } {1} {test}
 *
 * @since 0.0.3
 * @see Formatter
 */
object BracketStyle : AbstractFormatter('{', '}')
