package kingmc.util

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Standard game tick utilities, in minecraft a game
 * tick is equals to 0.05 second, 20 game ticks equal 1
 * second
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Utility
object Ticks {
    /**
     * Converts ticks into a [Duration].
     *
     * @see Duration
     * @param ticks the number of ticks
     * @return a duration
     * @since 0.0.1
     */
    @Deprecated("Use extension Int.ticks instead")
    fun duration(ticks: Long): Duration {
        return (ticks * SINGLE_TICK_DURATION_MS).milliseconds
    }

    /**
     * The number of ticks that occur in one second.
     *
     * @since 0.0.1
     */
    const val TICKS_PER_SECOND = 20

    /**
     * A single tick duration, in milliseconds.
     *
     * @since 0.0.1
     */
    const val SINGLE_TICK_DURATION_MS = (1000 / TICKS_PER_SECOND).toLong()
}

/**
 * The value of this duration expressed as a whole number of game ticks (20 per second)
 */
val Duration.inTicks: Long
    get() = this.inWholeMilliseconds / Ticks.SINGLE_TICK_DURATION_MS

/**
 * Returns a Duration equal to this `Int` number of ticks
 */
val Int.ticks: Duration
    get() = ((this * Ticks.SINGLE_TICK_DURATION_MS).milliseconds)

/**
 * Returns a Duration equal to this `Long` number of ticks
 */
val Long.ticks: Duration
    get() = ((this * Ticks.SINGLE_TICK_DURATION_MS).milliseconds)