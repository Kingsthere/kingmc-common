package kingmc.common.application

import kingmc.common.context.Context
import kingmc.common.context.LifecycleContext
import kingmc.util.ReloadableManager
import kingmc.util.ReloadableScope
import kingmc.util.lifecycle.Lifecycle
import java.util.*

/**
 * An application is the central class for using kingmc api, you
 * run the kingmc framework, and all controls should be passed through
 * the [Application]. Application instances should be available everywhere,
 * such as separate extensions, drivers... they should all have an
 * isolated application, so kingmc can easily enable/disable them, for
 * example: When the isolated extension disables all the things that should
 * be disabled by the extension using this api call, such as:
 *  + Scheduled tasks
 *  + Listeners
 *  + Commands
 *
 *
 * When a server has multiple extensions, this could prevent compatible problems
 * between them
 *
 * @author kingsthere
 * @since 0.0.2
 */
interface Application {
    /**
     * The `Context` that this application loading beans from
     */
    val context: Context

    /**
     * The environment of this application
     */
    val environment: ApplicationEnvironment

    /**
     * The name of this application
     */
    val name: String

    /**
     * The application local map of this `Application`
     */
    val applicationLocalMap: ApplicationLocalMap

    /**
     * The `ReloadableManager` of this application
     */
    val reloadableManager: ReloadableManager

    /**
     * Shutdown this application
     */
    fun shutdown()

    /**
     * Add a shutdown hook to this application, the [shutdownHook] will
     * be executed when this application [shutdown]
     *
     * @param shutdownHook the shutdown hook to execute when this application [shutdown]
     */
    fun addShutdownHook(shutdownHook: () -> Unit)
}

/**
 * Gets the properties of this application
 */
val Application.properties: Properties
    get() = this.context.properties

/**
 * Gets the lifecycle of this application
 */
val Application.lifecycle: Lifecycle
    get() = (this.context as? LifecycleContext)?.getLifecycle()
        ?: throw UnsupportedOperationException("Current context does not supported for lifecycle")

/**
 * A shortcut to reload all `Reloadable`s that are registered in the [Application.reloadableManager]
 */
fun Application.reloadAll() = reloadableManager.reloadAll()

/**
 * A shortcut to reload `Reloadable`s with the given [reloadableScope] that are
 * registered in the [Application.reloadableManager]
 */
fun Application.reload(reloadableScope: ReloadableScope) = reloadableManager.reload(reloadableScope)