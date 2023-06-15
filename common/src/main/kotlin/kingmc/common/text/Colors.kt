package kingmc.common.text

import net.kyori.adventure.text.format.TextColor
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

val DEEP_RED = TextColor.fromHexString("#AA0000")
val RED = TextColor.fromHexString("#FF5555")
val GOLD = TextColor.fromHexString("#FFAA00")
val YELLOW = TextColor.fromHexString("#FFFF55")
val GREEN = TextColor.fromHexString("#55FF55")
val DARK_GREEN = TextColor.fromHexString("#00AA00")
val AQUA = TextColor.fromHexString("#55FFFF")
val DARK_AQUA = TextColor.fromHexString("#00AAAA")
val BLUE = TextColor.fromHexString("#5555FF")
val DARK_BLUE = TextColor.fromHexString("#0000AA")
val LIGHT_PURPLE = TextColor.fromHexString("#FF55FF")
val DARK_PURPLE = TextColor.fromHexString("#AA00AA")
val WHITE = TextColor.fromHexString("#FFFFFF")
val GRAY = TextColor.fromHexString("#AAAAAA")
val DARK_GRAY = TextColor.fromHexString("#555555")
val BLACK = TextColor.fromHexString("#000000")