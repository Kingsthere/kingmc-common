package kingmc.common.text.serializer

import kingmc.common.text.Text
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

typealias GsonComponentSerializer = GsonComponentSerializer

/**
 * Current legacy component serializer to serialize
 * components to string
 *
 * @author kingsthere
 * @since 0.0.3
 * @see LegacyComponentSerializer
 */
val gsonComponentSerializer = GsonComponentSerializer.builder()
    .build()

/**
 * Serialize from [Text] to json string
 *
 * @author kingsthere
 * @since 0.0.3
 * @see LegacyComponentSerializer
 */
fun Text.serializeFromTextToJson(): String =
    gsonComponentSerializer.serialize(this)

/**
 * Deserialize from json string to [Text]
 *
 * @author kingsthere
 * @since 0.0.3
 * @see LegacyComponentSerializer
 */
fun String.deserializeFromJsonToText(): Text =
    gsonComponentSerializer.deserialize(this)