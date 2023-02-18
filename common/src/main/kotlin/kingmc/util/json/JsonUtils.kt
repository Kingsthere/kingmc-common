package kingmc.util.json

/**
 * Serialize an object to json
 *
 * @since 0.0.1
 * @author kingsthere
 */
fun Any.serializeToJson(): String =
    Gsons.standard.toJson(this)

/**
 * Deserialize an object from json
 *
 * @since 0.0.1
 * @author kingsthere
 */
inline fun <reified T : Any> String.deserializeFromJson(): T =
    Gsons.standard.fromJson(this, T::class.java)

