package kingmc.util.text

import kingmc.common.text.Text

/**
 * A superinterface represent an object that capable to convert
 * into a [Text]
 *
 * @since 0.0.3
 * @author kingsthere
 * @see Text
 */
interface TextDisplayable {
    /**
     * Convert this object into a [Text]
     */
    fun asText(): Text
}