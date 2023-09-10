package kingmc.common.text

/**
 * The [HSVLike.h] component.
 *
 * Allows for destructuring into `(h, s, v)`.
 *
 * @return the [HSVLike.h] component
 */
operator fun HSVLike.component1(): Float = h()

/**
 * The [HSVLike.s] component.
 *
 * Allows for destructuring into `(h, s, v)`.
 *
 * @return the [HSVLike.s] component
 */
operator fun HSVLike.component2(): Float = s()

/**
 * The [HSVLike.v] component.
 *
 * Allows for destructuring into `(h, s, v)`.
 *
 * @return the [HSVLike.v] component
 */
operator fun HSVLike.component3(): Float = v()