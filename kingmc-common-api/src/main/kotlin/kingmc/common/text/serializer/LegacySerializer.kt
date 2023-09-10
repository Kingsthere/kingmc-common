package kingmc.common.text.serializer

import kingmc.common.text.Text
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

typealias LegacyComponentSerializer = LegacyComponentSerializer

/**
 * Current legacy component serializer to serialize
 * components to string
 *
 * @author kingsthere
 * @since 0.0.3
 * @see LegacyComponentSerializer
 */
val legacyComponentSerializer = LegacyComponentSerializer.builder()
    .hexColors()
    .build()

/**
 * Serialize from a [Text] to legacy string
 *
 * @author kingsthere
 * @since 0.0.3
 * @see LegacyComponentSerializer
 */
fun Text.serializeFromTextToLegacy(): String =
    legacyComponentSerializer.serialize(this)

/**
 * Deserialize from a legacy string to [Text]
 *
 * @author kingsthere
 * @since 0.0.3
 * @see LegacyComponentSerializer
 */
fun String.deserializeFromLegacyToText(): Text =
    legacyComponentSerializer.deserialize(this)