package kingmc.common.text

import net.kyori.adventure.text.BlockNBTComponent

/**
 * The X component of this world position.
 *
 * Allows for destructuring into `(x, y, z)`.
 *
 * @return the X component
 * @since 4.10.0
 */
operator fun BlockNBTComponent.WorldPos.component1(): BlockNBTComponent.WorldPos.Coordinate = x()

/**
 * The Y component of this world position.
 *
 * Allows for destructuring into `(x, y, z)`.
 *
 * @return the Y component
 * @since 4.10.0
 */
operator fun BlockNBTComponent.WorldPos.component2(): BlockNBTComponent.WorldPos.Coordinate = y()

/**
 * The Z component of this world position.
 *
 * Allows for destructuring into `(x, y, z)`.
 *
 * @return the Z component
 * @since 4.10.0
 */
operator fun BlockNBTComponent.WorldPos.component3(): BlockNBTComponent.WorldPos.Coordinate = z()