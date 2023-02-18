package kingmc.common.application

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Represents an environment in which [Application] runs
 *
 * @since 0.0.2
 * @author kingsthere
 * @see Application
 */
interface ApplicationEnvironment : CoroutineScope {

    /**
     * [ClassLoader] used to load application.
     *
     * Useful for various reflection-based services, like dependency injection.
     */
    val classLoader: ClassLoader

    /**
     * Parent coroutine context for an application
     */
    override val coroutineContext: CoroutineContext
}