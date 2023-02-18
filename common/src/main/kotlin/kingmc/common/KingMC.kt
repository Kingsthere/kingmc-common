package kingmc.common

import kingmc.util.Lifecycle
import kingmc.util.Utility

/**
 * The basic class of kingmc, used to specified
 * things about the server where the kingmc application
 * run on
 *
 *
 * And the utility methods for kingmc
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Utility
object KingMC {
    /**
     * The version number of kingmc framework
     */
    const val VERSION: String = "alpha-0.0.5"

    /**
     * The whole lifecycle of kingmc, start
     * from server load to server shutdown
     *
     * @see Lifecycle
     * @since 0.0.1
     */
    @Deprecated("Use lifecycle that declared in Application instead")
    private val lifecycle: Lifecycle<Runnable> by lazy {
        Lifecycle.builder<Runnable>()
            .build()!!
    }

    /**
     * Get the [Lifecycle] of kingmc
     *
     * @since 0.0.1
     * @see Lifecycle
     */
    @Deprecated("Use lifecycle that declare in Application instead")
    fun lifecycle(): Lifecycle<Runnable> = lifecycle

    /**
     * Add a postponed runnable into the lifecycle, the
     * runnable you specified will execute when lifecycle
     * reach the stage you specified
     *
     * @see Lifecycle
     * @see Runnable
     * @param lifecycle the lifecycle stage specified that when the lifecycle
     *                  reached the runnable will execute
     * @param runnable the runnable will execute when the lifecycle reach
     *                 the specified stage
     *
     * @since 0.0.1
     */
    @Deprecated("The global lifecycle is deprecated")
    fun postponeExecute(lifecycle: Int, runnable: Runnable) {
        // Insert the plan into lifecycle
        this.lifecycle.insertPlan(lifecycle, runnable)
    }

    /**
     * Add multiplies of postponed runnable into the lifecycle, the
     * runnable you specified will execute when lifecycle
     * reach the stage you specified
     *
     * @see Lifecycle
     * @see Runnable
     * @param lifecycle the lifecycle stage specified that when the lifecycle
     *                  reached the runnable will execute
     * @param runnable the runnable will execute when the lifecycle reach
     *                 the specified stage
     *
     * @since 0.0.1
     */
    @Deprecated("The global lifecycle is deprecated")
    fun postponeExecute(lifecycle: Int, vararg runnable: Runnable) {
        // Traverse many runnable specified and
        // insert them into the lifecycle
        for (item: Runnable in runnable) {
            this.lifecycle.insertPlan(lifecycle, item)
        }
    }
}
