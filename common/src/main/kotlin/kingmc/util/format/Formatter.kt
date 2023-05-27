package kingmc.util.format

import kingmc.util.SubclassSingleton

/**
 * Interface indicates a formatter to format strings
 *
 * @since 0.0.4
 * @author kingsthere
 */
@SubclassSingleton
interface Formatter {
    /**
     * Format specified [string] and return
     *
     * @param string String to format
     * @return String formatted
     */
    fun format(string: String, formatContext: FormatContext): String
}