package kingmc.common.context

import kingmc.util.Lifecycle
import kingmc.util.LifecycleHandler

/**
 * This superinterface indicating the subclasses context has
 * a [Lifecycle] to identity the stage where they are running
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface LifecycleContext : LifecycleHandler<Runnable>, Context {
    /**
     * Get the lifecycle of this context
     */
    override fun lifecycle(): Lifecycle<Runnable>
}