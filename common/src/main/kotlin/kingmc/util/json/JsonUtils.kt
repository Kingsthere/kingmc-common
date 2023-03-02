package kingmc.util.json

import kingmc.util.json.gson.StandardGson

/**
 * Serialize an object to json
 *
 * @since 0.0.1
 * @author kingsthere
 */
fun Any.serializeToJson(): String =
    StandardGson.standard.toJson(this)

/**
 * Deserialize an object from json
 *
 * @since 0.0.1
 * @author kingsthere
 */
inline fun <reified T : Any> String.deserializeFromJson(): T =
    StandardGson.standard.fromJson(this, T::class.java)

