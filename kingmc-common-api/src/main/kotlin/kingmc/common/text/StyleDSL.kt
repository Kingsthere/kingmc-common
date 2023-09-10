package kingmc.common.text

import kingmc.common.text.style.TextStyle
import kingmc.common.text.style.TextStyleBuilder
import net.kyori.adventure.text.format.Style
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Builds a new [TextStyle] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [TextStyle]
 * @since 0.0.7
 */
@OptIn(ExperimentalContracts::class)
fun TextStyle(builder: TextStyleBuilder.() -> Unit): TextStyle {
    contract {
        callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
    }
    return Style.style(builder)
}

/**
 * Allows editing using [TextStyleBuilder] as the receiver parameter.
 *
 * @param consumer the consumer to edit this TextStyle with
 * @return a new TextStyle, with the changes applied from this builder
 * @since 0.0.7
 */
@OptIn(ExperimentalContracts::class)
fun TextStyle.edit(consumer: TextStyleBuilder.() -> Unit): TextStyle {
    contract {
        callsInPlace(consumer, InvocationKind.EXACTLY_ONCE)
    }
    return edit(consumer)
}