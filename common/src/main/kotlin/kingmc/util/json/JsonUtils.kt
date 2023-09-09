package kingmc.util.json

import kingmc.util.json.gson.StandardGson

/**
 * Serialize an object to json
 *
 * @author kingsthere
 * @since 0.0.1
 */
fun Any.serializeToJson(): String =
    StandardGson.standard.toJson(this)

/**
 * Deserialize an object from json
 *
 * @author kingsthere
 * @since 0.0.1
 */
inline fun <reified T : Any> String.deserializeFromJson(): T =
    StandardGson.standard.fromJson(this, T::class.java)

