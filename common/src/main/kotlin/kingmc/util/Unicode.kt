@file:Utility

package kingmc.util

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Encode string to a unicodes
 *
 * @since 0.0.1
 * @author kingsthere
 */
fun String.encodeUnicode(): String {
    val utfBytes: CharArray = this.toCharArray()
    var unicodeBytes = ""
    for (i in utfBytes.indices) {
        var hexB = Integer.toHexString(utfBytes[i].code)
        if (hexB.length <= 2) {
            hexB = "00$hexB"
        }
        unicodeBytes = "$unicodeBytes\\u$hexB"
    }
    return unicodeBytes
}

val unicodePattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))")!!

/**
 * Decode unicodes from this string
 *
 * @since 0.0.1
 * @author kingsthere
 */
fun String.decodeUnicode(): String {
    val matcher: Matcher = unicodePattern.matcher(this)
    var ch: Char
    var newString = this
    while (matcher.find()) {
        ch = matcher.group(2).toInt(16).toChar()
        newString = newString.replace(matcher.group(1), ch.toString() + "")
    }
    return newString
}
