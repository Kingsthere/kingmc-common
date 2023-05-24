package kingmc.common.application

import kingmc.util.KingMCDsl
import java.util.concurrent.atomic.AtomicReference
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A `ApplicationManager` is responsible for binding applications to current
 * thread and get bound application to current thread
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface ApplicationManager {

    fun currentOrNull(): Application?

    fun bindApplicationToThread(application: Application?)

    companion object {
        fun currentOrNull(): Application? = applicationManager.currentOrNull()

        fun bindApplicationToThread(application: Application?) = applicationManager.bindApplicationToThread(application)

        fun current(): Application = applicationManager.currentOrNull() ?: throw IllegalStateException()
    }
}

/**
 * Implemented [ApplicationManager] using [ThreadLocal]
 *
 * @since 0.0.3
 * @author kingsthere
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

fun <T> keepAndRestoreApplicationRefAfterRun(block: () -> T): T {
    val currentApplication = applicationManager.currentOrNull()
    return try {
        block()
    } finally {
        applicationManager.bindApplicationToThread(currentApplication)
    }
}

suspend fun <T> keepAndRestoreApplicationRefAfterRunSuspend(block: suspend () -> T): T {
    val currentApplication = applicationManager.currentOrNull()
    return try {
        block()
    } finally {
        applicationManager.bindApplicationToThread(currentApplication)
    }
}

internal val applicationManager: ApplicationManager by lazy {
    ThreadLocalApplicationManager()
}

/**
 * Run a block of action surround with application, the codes surrounded with `application()`
 * should run like this
 * ```
 * // Application set to ApplicationManager
 * withApplication {
 *     actionsWithApplication()
 * } // Application removed from ApplicationManager
 * ```
 *
 * Whether to use `application {  }` to surround these operation depends on whether
 * the function involved in the code has a [WithApplication] annotation
 *
 * @param action the action to run with application set
 */
@OptIn(ExperimentalContracts::class)
@KingMCDsl
fun <R> Any.withApplication(action: @WithApplication Application.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    return withApplication(this.application, action)
}

/**
 * Run a block of action surround with application, the codes surrounded with `application()`
 * should run like this
 * ```
 * // Application set to ApplicationManager
 * withApplication {
 *     actionsWithApplication()
 * } // Application removed from ApplicationManager
 * ```
 *
 * Whether to use `application {  }` to surround these operation depends on whether
 * the function involved in the code has a [WithApplication] annotation
 *
 * @param application the application set to the ApplicationManager
 * @param action the action to run with application set
 */
@OptIn(ExperimentalContracts::class)
@KingMCDsl
fun <R> withApplication(application: Application, action: @WithApplication Application.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    val result: AtomicReference<R> = AtomicReference()
    keepAndRestoreApplicationRefAfterRun {
        ApplicationManager.bindApplicationToThread(application)
        result.set(application.action())
    }
    return result.get()
}

/**
 * Run a block of suspended action surround with application, the codes surrounded with `application()`
 * should run like this
 * ```
 * // Application set to ApplicationManager
 * withApplicationSuspend {
 *     suspendActionsWithApplication()
 * } // Application removed from ApplicationManager
 * ```
 *
 * Whether to use `suspendApplication {  }` to surround these operation depends on whether
 * the function involved in the code has a [WithApplication] annotation
 *
 * @param action the action to run with application set
 */
@OptIn(ExperimentalContracts::class)
@KingMCDsl
suspend fun <R> Any.withApplicationSuspend(action: @WithApplication suspend Application.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    val application = this.application
    return keepAndRestoreApplicationRefAfterRunSuspend {
        ApplicationManager.bindApplicationToThread(application)
        application.action()
    }
}

/**
 * Run a block of suspended action surround with application, the codes surrounded with `application()`
 * should run like this
 * ```
 * // Application set to ApplicationManager
 * withApplicationSuspend {
 *     suspendActionsWithApplication()
 * } // Application removed from ApplicationManager
 * ```
 *
 * Whether to use `suspendApplication {  }` to surround these operation depends on whether
 * the function involved in the code has a [WithApplication] annotation
 *
 * @param application the application set to the ApplicationManager
 * @param action the action to run with application set
 */
@OptIn(ExperimentalContracts::class)
@KingMCDsl
suspend fun <R> withApplicationSuspend(application: Application, action: @WithApplication suspend Application.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    return keepAndRestoreApplicationRefAfterRunSuspend {
        ApplicationManager.bindApplicationToThread(application)
        application.action()
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
    ApplicationManager.currentOrNull() ?: throw IllegalStateException("Application is not set, you must call application() before using this statement (Did you forget add application {  } before running this statement?)")