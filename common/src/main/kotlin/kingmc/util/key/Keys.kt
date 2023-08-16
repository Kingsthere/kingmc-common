@file:Utility

package kingmc.util.key

import kingmc.util.Utility

/**
 * Create a key by using the string with specify
 * format (**namespace:value**)
 *
 * @since 0.0.1
 * @author kingsthere
 * @param string the string to extract from
 * @return the key extract from string
 */
@Throws(InvalidKeyException::class)
fun keyOf(string: String): Key {
    val strings = string.split(":")
    if (strings.size != 2) {
        throw MalformedKeyException(string)
    }
    return Key(strings[0], strings[1])
}

/**
 * Create a key by using current string with specify
 * format (**namespace:value**)
 *
 * @since 0.0.1
 * @author kingsthere
 * @return the key extract from string
 */
@Throws(InvalidKeyException::class)
fun String.toKey(): Key =
    keyOf(this)

/**
 * Convert this object to a [Key]
 *
 * @since 0.0.1
 * @author kingsthere
 * @return the key converted to
 */
@Throws(InvalidKeyException::class)
fun Any.toKey() =
    keyOf(this.toString())