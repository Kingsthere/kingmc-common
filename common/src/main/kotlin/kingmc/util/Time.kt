package kingmc.util

import java.time.Duration

/**
 * Standard game tick utilities, in minecraft a game
 * tick is equals to 0.05 second, 20 game tick equals 1
 * second
 *
 * @since 0.0.1
 * @author kingsthere
 */
object Ticks {
    /**
     * Converts ticks into a [Duration].
     *
     * @see Duration
     * @param ticks the number of ticks
     * @return a duration
     * @since 0.0.1
     */
    fun duration(ticks: Long): Duration {
        return Duration.ofMillis(ticks * SINGLE_TICK_DURATION_MS)
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
