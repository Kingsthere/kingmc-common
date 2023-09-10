package kingmc.common.application

import kingmc.common.context.Context
import kingmc.util.InternalAPI
import kingmc.util.KingMCDsl
import kingmc.util.lifecycle.Lifecycle
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A `ApplicationManager` is responsible for binding applications to current
 * thread and get bound application to current thread
 *
 * @author kingsthere
 * @since 0.0.3
 */
interface ApplicationManager {

    fun currentOrNull(): Application?

    fun bindApplicationToThread(application: Application?)

    companion object {
        @OptIn(InternalAPI::class)
        fun currentOrNull(): Application? = applicationManager.currentOrNull()

        @OptIn(InternalAPI::class)
        fun bindApplicationToThread(application: Application?) = applicationManager.bindApplicationToThread(application)

        @OptIn(InternalAPI::class)
        fun current(): Application = applicationManager.currentOrNull() ?: throw IllegalStateException()
    }
}

/**
 * Implemented [ApplicationManager] using [ThreadLocal]
 *
 * @author kingsthere
 * @since 0.0.3
 */
class ThreadLocalApplicationManager : ApplicationManager {
    private val threadLocal = ThreadLocal<Application>()

    override fun currentOrNull(): Application? {
        return threadLocal.get()
    }

    override fun bindApplicationToThread(application: Application?) {
        if (application != null) {
            threadLocal.set(application)
        } else {
            threadLocal.remove()
        }
    }
}

@OptIn(InternalAPI::class)
inline fun <T> keepAndRestoreApplicationRefAfterRun(block: () -> T): T {
    val currentApplication = applicationManager.currentOrNull()
    return try {
        block()
    } finally {
        applicationManager.bindApplicationToThread(currentApplication)
    }
}

/**
 * The default application manager to kingmc framework
 */
@InternalAPI
val applicationManager: ApplicationManager by lazy {
    ThreadLocalApplicationManager()
}

/**
 * Run a block of action surround with receiver's application, the codes surrounded with `application()`
 * should run like this
 * ```
 * // Application set to ApplicationManager
 * withApplication {
 *     actionsWithApplication()
 * } // Application removed from ApplicationManager
 * ```
 *
 * Whether to use `application {  }` to surround these operations depends on whether
 * the function involved in the code has a [WithApplication] annotation
 *
 * @param action the action to run with application set
 */
@OptIn(ExperimentalContracts::class)
@KingMCDsl
inline fun <R> Any.withApplication(action: @WithApplication Application.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    return withApplication(this.application, action)
}

/**
 * Run a block of action surround with the given application, the codes surrounded with `application()`
 * should run like this
 * ```
 * // Application set to ApplicationManager
 * withApplication {
 *     actionsWithApplication()
 * } // Application removed from ApplicationManager
 * ```
 *
 * Whether to use `application {  }` to surround these operations depends on whether
 * the function involved in the code has a [WithApplication] annotation
 *
 * @param application the application set to the ApplicationManager
 * @param action the action to run with application set
 */
@OptIn(ExperimentalContracts::class)
@KingMCDsl
inline fun <R> withApplication(application: Application, action: @WithApplication Application.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    return keepAndRestoreApplicationRefAfterRun {
        ApplicationManager.bindApplicationToThread(application)
        action(application)
    }
}

/**
 * Gets the current application from `ApplicationManager`
 *
 * @return application or `null` if application is not set in current thread
 */
fun currentApplicationOrNull(): Application? =
    ApplicationManager.currentOrNull()

/**
 * Gets the current application from `ApplicationManager`
 *
 * @throws IllegalStateException if the `Application` is not set
 * @return application
 */
fun currentApplication(): Application =
    ApplicationManager.currentOrNull()
        ?: throw IllegalStateException("Application is not set, you must call application() before using this statement (Did you forget add application {  } before running this statement?)")

/**
 * Gets the current context of the current application
 *
 * @return context of the current application
 */
fun currentContext(): Context = currentApplication().context

/**
 * Gets the current context's lifecycle of the current application
 *
 * @return context's lifecycle of the current application
 */
fun currentLifecycle(): Lifecycle = currentApplication().lifecycle