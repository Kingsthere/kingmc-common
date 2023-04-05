package kingmc.common.text

import net.kyori.adventure.util.HSVLike
import net.kyori.adventure.util.RGBLike
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

typealias RGBLike = RGBLike
typealias HSVLike = HSVLike

/**
 * Generate a hex color from [String]
 *
 * @since 0.0.4
 * @author kingsthere
 */
fun generateHexColorFromString(string: String): String {
    val md: MessageDigest = try { MessageDigest.getInstance("MD5") } catch (var3: NoSuchAlgorithmException) { throw InternalError("MD5 not supported", var3) }
    val md5Bytes = md.digest(string.toByteArray(Charsets.UTF_8)).toString()
    return "#${md5Bytes[3]}${md5Bytes[4]}${md5Bytes[5]}${md5Bytes[6]}${md5Bytes[7]}${md5Bytes[8]}"
}