package kingmc.common.application

import kingmc.common.context.Context

/**
 * An application is the central class for using kingmc api, you
 * run the kingmc framework with [kingmc.common.boot.ApplicationBoot] and it gives out of
 * an [Application]
 *
 *
 * Application provide you programming interfaces to use the apis
 * in kingmc framework
 *
 *
 * A project(that uses kingmc framework) could have many [Application]
 * created if you want, for example you want to create plugin modules/extensions,
 * then you can use [kingmc.common.boot.ApplicationBoot] to load them and simply manage then
 * using [Application] to shut down, run, reload...
 *
 * @since 0.0.2
 * @author kingsthere
 * @param TContext the type of context that loading this application
 */
interface Application<TContext : Context> {
    /**
     * The [TContext] that loading this application
     */
    val context: TContext

    /**
     * The environment of this application
     */
    val environment: ApplicationEnvironment

    /**
     * The name of this application
     */
    val name: String

    /**
     * Called to dispose/terminate this application
     */
    fun dispose()
}