/**
 * Utility things for minecraft uses in kingmc
 * audience api about **chat**
 *
 * @since 0.0.3
 * @author kingsthere
 */

package kingmc.common.text

import net.kyori.adventure.nbt.api.BinaryTagHolder
import net.kyori.adventure.text.*
import net.kyori.adventure.text.minimessage.MiniMessage

/*
Alias types from kyori adventure api
to kingmc to avoid classnames collision
 */
typealias Text = Component
typealias LiteralText = TextComponent
typealias TextApplicable = ComponentApplicable
typealias TextBuilderApplicable = ComponentBuilderApplicable
typealias BuildableText<C, B> = BuildableComponent<C, B>
typealias TextBuilder<C, B> = ComponentBuilder<C, B>
typealias StringText = TextComponent

typealias TranslatableText = TranslatableComponent
typealias SelectorText = SelectorComponent
typealias ScoreText = ScoreComponent
typealias ScopedText<C> = ScopedComponent<C>
typealias EntityNBTText = EntityNBTComponent
typealias BlockNBTText = BlockNBTComponent
typealias StorageNBTText = StorageNBTComponent
typealias KeybindText = KeybindComponent

typealias LiteralTextBuilder = TextComponent.Builder
typealias TranslatableTextBuilder = TranslatableComponent.Builder
typealias SelectorTextBuilder = SelectorComponent.Builder
typealias ScoreTextBuilder = ScoreComponent.Builder
typealias EntityNBTTextBuilder = EntityNBTComponent.Builder
typealias BlockNBTTextBuilder = BlockNBTComponent.Builder
typealias StorageNBTTextBuilder = StorageNBTComponent.Builder
typealias KeybindTextBuilder = KeybindComponent.Builder

typealias BinaryTagHolder = BinaryTagHolder

/*
Aliases from minimessage
to kingmc
 */
typealias MiniMessage = MiniMessage
typealias TextStyleTag = Mark
