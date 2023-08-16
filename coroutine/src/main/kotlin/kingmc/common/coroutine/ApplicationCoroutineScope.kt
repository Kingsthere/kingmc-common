package kingmc.common.coroutine

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * An `ApplicationCoroutineScope` is a coroutine scope implementation used by kingmc framework,
 * when application shutdown this coroutine scope automatically cancelled
 *
 * @since 0.0.7
 * @author kingsthere
 */
open class ApplicationCoroutineScope @WithApplication internal constructor(
    val application: Application = currentApplication(),
    override val coroutineContext: CoroutineContext = syncMinecraftCoroutineDispatcher,
) : CoroutineScope {
    init {
        // Add shutdown hook to cancel this coroutine scope
        this.application.addShutdownHook {
            if (isActive) {
                this.cancel(CancellationException("Application shutdown"))
            }
        }
    }

    override fun toString(): String {
        return "ApplicationCoroutineScope(application=$application, coroutineContext=$coroutineContext)"
    }
}

/**
 * Create an application coroutine scope for current application
 *
 * @param context the coroutine context for this scope
 * @param application the application for this scope
 * @since 0.0.7
 * @author kingsthere
 */
@WithApplication
fun ApplicationCoroutineScope(
    context: CoroutineContext = asyncMinecraftCoroutineDispatcher,
    application: Application = currentApplication(),
): ApplicationCoroutineScope = ApplicationCoroutineScope(application, if (context[Job] != null) context else context + Job())