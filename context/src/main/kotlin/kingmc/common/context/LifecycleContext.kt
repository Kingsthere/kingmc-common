package kingmc.common.context

import kingmc.util.lifecycle.Lifecycle

/**
 * This superinterface indicating the subclasses context has
 * a [Lifecycle] to identity the stage where they are running
 *
 * @author kingsthere
 * @since 0.0.1
 */
interface LifecycleContext : Context {
    /**
     * Get the lifecycle of this context
     */
    fun getLifecycle(): Lifecycle
}