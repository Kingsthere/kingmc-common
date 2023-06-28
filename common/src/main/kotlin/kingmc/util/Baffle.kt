package kingmc.util

import kingmc.util.builder.Buildable
import kotlin.time.Duration

/**
 * A baffle is a light-weight time counter, baffle
 * usually used to apply cooldown to baffle objects,
 * a baffle like a map can store many baffle objects,
 * and count them isolated by the specifying duration
 * cooldown
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface Baffle<T> {
    /**
     * Reset all baffle objects that
     * registered in this baffle
     *
     * @since 0.0.1
     */
    fun resetAll()

    /**
     * Reset the cache of the specifying
     * baffle object
     *
     * @param obj the baffle object to reset
     * @since 0.0.1
     */
    fun reset(obj: T)

    /**
     * Force update the cooldown of specifying
     * baffle object into zero
     *
     * @param obj the object to update
     * @since 0.0.1
     */
    fun next(obj: T)

    /**
     * To verify if the baffle object specifying is
     * not on baffle cooldown
     *
     * @param obj the baffle object to check
     * @return If the object specifying is
     *         not on cooldown
     * @since 0.0.1
     */
    fun hasNext(obj: T): Boolean

    /**
     * A Builder to build a [Baffle] with
     * customize options
     *
     * @since 0.0.1
     */
    interface Builder<T> : Buildable.Builder<Baffle<T>> {
        /**
         * Set the cooldown to the baffle
         *
         * @since 0.0.1
         * @see Duration
         */
        fun cooldown(duration: Duration): Builder<T>
    }
}