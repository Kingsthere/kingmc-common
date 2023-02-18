package kingmc.common.application

import kingmc.common.context.Context
import kingmc.util.KingMCDsl
import java.util.concurrent.atomic.AtomicReference
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ApplicationManager {

    fun currentOrNull(): Application<*>?

    fun bindApplicationToThread(application: Application<*>?)

    companion object {
        fun currentOrNull(): Application<*>? = applicationManager.currentOrNull()

        fun bindApplicationToThread(application: Application<*>?) = applicationManager.bindApplicationToThread(application)

        fun current(): Application<*> = applicationManager.currentOrNull() ?: throw IllegalStateException()
    }
}

class ThreadLocalApplicationManager : ApplicationManager {
    private val threadLocal = ThreadLocal<Application<*>>()

    override fun currentOrNull(): Application<*>? {
        return threadLocal.get()
    }

    override fun bindApplicationToThread(application: Application<*>?) {
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

@OptIn(ExperimentalContracts::class)
@KingMCDsl
fun <R> Any.application(application: Application<*> = this.application, action: @WithApplication Application<out Context>.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    val result: AtomicReference<R> = AtomicReference()
    keepAndRestoreApplicationRefAfterRun {
        ApplicationManager.bindApplicationToThread(application)
        result.set(application.action())
    }
    return result.get()
}

@OptIn(ExperimentalContracts::class)
@KingMCDsl
suspend fun <R> Any.suspendApplication(application: Application<*> = this.application, action: @WithApplication suspend Application<out Context>.() -> R): R {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    val result: AtomicReference<R> = AtomicReference()
    keepAndRestoreApplicationRefAfterRunSuspend {
        ApplicationManager.bindApplicationToThread(application)
        result.set(application.action())
    }
    return result.get()
}

fun currentApplicationOrNull(): Application<*>? =
    ApplicationManager.currentOrNull()

fun currentApplication(): Application<*> =
    ApplicationManager.currentOrNull() ?: throw IllegalStateException("Application is not set, you must call application() before using this statement (Did you forget add application {  } before running this statement?)")