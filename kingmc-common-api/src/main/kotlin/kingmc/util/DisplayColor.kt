package kingmc.util

/**
 * A utility enum class describe simple colors in
 * minecraft
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Utility
enum class DisplayColor(val colorCode: String) {
    DARK_RED("5"),
    RED("c"),
    GOLD("6"),
    YELLOW("e"),
    DARK_GREEN("2"),
    GREEN("a"),
    DARK_AQUA("3"),
    AQUA("b"),
    DARK_BLUE("1"),
    BLUE("9"),
    LIGHT_PURPLE("d"),
    DARK_PURPLE("5"),
    WHITE("f"),
    GRAY("7"),
    DARK_GRAY("8"),
    BLACK("0");

    /**
     * Convert this color to a string with [COLOR_CHAR]
     */
    override fun toString(): String = "$COLOR_CHAR$colorCode"
}

/**
 * The color char in minecraft java (\u00A7)
 *
 * @author kingsthere
 * @since 0.0.2
 */
const val COLOR_CHAR = '\u00A7'