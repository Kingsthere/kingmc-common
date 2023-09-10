package kingmc.util.text

import kingmc.common.text.Text

/**
 * A superinterface represent an object that capable to convert
 * into a [Text]
 *
 * @author kingsthere
 * @since 0.0.3
 * @see Text
 */
interface TextDisplayable {
    /**
     * Convert this object into a [Text]
     */
    fun asText(): Text
}

val TEXT_NULL = Text("null")

/**
 * Converts this object into a [Text] reference, if this object implements [TextDisplayable]
 * interface it will give the [TextDisplayable.asText] as result, otherwise it will return a
 * plain text with literal [toString] as a result
 *
 * @receiver object to convert as a text
 * @return text converted
 */
fun Any?.toText(): Text {
    return if (this == null) {
        // Simply return null
        TEXT_NULL
    } else {
        if (this is TextDisplayable) {
            // Return TextDisplayable.asText() as result
            this.asText()
        } else {
            // Return plain text with "this.toString()" as result
            Text(this.toString())
        }
    }
}