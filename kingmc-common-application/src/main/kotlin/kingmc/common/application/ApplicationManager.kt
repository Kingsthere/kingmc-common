package kingmc.common.application

import kingmc.common.context.Context
import kingmc.util.KingMCDsl
import kingmc.util.lifecycle.Lifecycle
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun <T> keepAndRestoreApplicationRefAfterRun(block: () -> T): T {
    val currentApplication = ThreadLocalApplicationManager.currentOrNull()
    return try {
        block()
    } finally {
        ThreadLocalApplicationManager.bindApplicationToThread(currentApplication)
    }
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
        ThreadLocalApplicationManager.bindApplicationToThread(application)
        action(application)
    }
}

/**
 * Gets the current application from `ApplicationManager`
 *
 * @return application or `null` if application is not set in current thread
 */
fun currentApplicationOrNull(): Application? =
    ThreadLocalApplicationManager.currentOrNull()

/**
 * Gets the current application from `ApplicationManager`
 *
 * @throws IllegalStateException if the `Application` is not set
 * @return application
 */
fun currentApplication(): Application =
    ThreadLocalApplicationManager.currentOrNull()
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