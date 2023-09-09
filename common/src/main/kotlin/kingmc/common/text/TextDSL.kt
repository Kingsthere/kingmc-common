package kingmc.common.text

import kingmc.util.text.HoverEventDisplayable
import kingmc.util.text.TextDisplayable

/**
 * Builds a new [BlockNBTText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [BlockNBTText]
 * @since 0.0.7
 */
fun BlockNBTText(builder: BlockNBTTextBuilder.() -> Unit): BlockNBTText = Text.blockNBT(builder)

/**
 * Builds a new [EntityNBTText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [EntityNBTText]
 * @since 0.0.7
 */
fun EntityNBTText(builder: EntityNBTTextBuilder.() -> Unit): EntityNBTText = Text.entityNBT(builder)

/**
 * Builds a new [KeybindText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [KeybindText]
 * @since 0.0.7
 */
fun KeybindText(builder: KeybindTextBuilder.() -> Unit): KeybindText = Text.keybind(builder)

/**
 * Builds a new [ScoreText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [ScoreText]
 * @since 0.0.7
 */
fun ScoreText(builder: ScoreTextBuilder.() -> Unit): ScoreText = Text.score(builder)

/**
 * Builds a new [SelectorText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [SelectorText]
 * @since 0.0.7
 */
fun SelectorText(builder: SelectorTextBuilder.() -> Unit): SelectorText = Text.selector(builder)

/**
 * Builds a new [StorageNBTText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [StorageNBTText]
 * @since 0.0.7
 */
fun StorageNBTText(builder: StorageNBTTextBuilder.() -> Unit): StorageNBTText = Text.storageNBT(builder)

/**
 * Builds a new [LiteralText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [LiteralText]
 * @since 0.0.7
 */
fun Text(builder: LiteralTextBuilder.() -> Unit = { }): Text = Text.text(builder)

/**
 * Builds a new [LiteralText] with default content set from
 * the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [LiteralText]
 * @since 0.0.7
 */
fun Text(content: String, builder: LiteralTextBuilder.() -> Unit = { }): Text =
    Text.text {
        it.content(content)
        it.builder()
    }

/**
 * Builds a new [TranslatableText] from the specified [builder].
 *
 * @param builder the builder to apply values from
 * @return a new [TranslatableText]
 * @since 0.0.7
 */
fun TranslatableText(builder: TranslatableTextBuilder.() -> Unit): TranslatableText = Text.translatable(builder)

/**
 * Appends a text from [textDisplayable] to this text
 *
 * @author kingsthere
 * @since 0.0.7
 */
fun <C : BuildableText<C, B>, B : TextBuilder<C, B>> TextBuilder<C, B>.append(textDisplayable: TextDisplayable): TextBuilder<C, B> {
    return this.append(textDisplayable.asText())
}

/**
 * Appends multiple text from [textDisplayable] to this text
 *
 * @author kingsthere
 * @since 0.0.7
 */
fun <C : BuildableText<C, B>, B : TextBuilder<C, B>> TextBuilder<C, B>.append(vararg textDisplayable: TextDisplayable): TextBuilder<C, B> {
    return this.append(textDisplayable.map { it.asText() })
}

/**
 * Sets the hover event of this text to [hoverEventDisplayable]
 *
 * @author kingsthere
 * @since 0.0.7
 */
fun <C : BuildableText<C, B>, B : TextBuilder<C, B>> TextBuilder<C, B>.hoverEvent(hoverEventDisplayable: HoverEventDisplayable): TextBuilder<C, B> {
    return this.hoverEvent(hoverEventDisplayable.asHoverEvent())
}

/**
 * A [Text] with empty contents
 *
 * @author kingsthere
 * @since 0.0.7
 */
val TEXT_EMPTY: Text = Text()