package kingmc.common.text.serializer

import kingmc.common.text.Text
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

typealias GsonComponentSerializer = GsonComponentSerializer

/**
 * Current legacy component serializer to serialize
 * components to string
 *
 * @since 0.0.3
 * @author kingsthere
 * @see LegacyComponentSerializer
 */
val gsonComponentSerializer = GsonComponentSerializer.builder()
    .build()

/**
 * Serialize from [Text] to json string
 *
 * @since 0.0.3
 * @author kingsthere
 * @see LegacyComponentSerializer
 */
fun Text.serializeFromTextToJson(): String =
    gsonComponentSerializer.serialize(this)

/**
 * Deserialize from json string to [Text]
 *
 * @since 0.0.3
 * @author kingsthere
 * @see LegacyComponentSerializer
 */
fun String.deserializeFromJsonToText(): Text =
    gsonComponentSerializer.deserialize(this)