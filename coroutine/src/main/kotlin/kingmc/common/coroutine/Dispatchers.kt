package kingmc.common.coroutine

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Gets a coroutine dispatcher from current context
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@WithApplication
fun CoroutineDispatcher(): CoroutineDispatcher {
    return currentApplication().context.getBean(CoroutineDispatcher::class)
}

/**
 * Gets an async minecraft coroutine dispatcher from current context
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val asyncMinecraftCoroutineDispatcher: AsyncMinecraftCoroutineDispatcher
    get() = currentApplication().context.getBean(AsyncMinecraftCoroutineDispatcher::class)

/**
 * Gets an async minecraft coroutine dispatcher from current context
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val syncMinecraftCoroutineDispatcher: SyncMinecraftCoroutineDispatcher
    get() = currentApplication().context.getBean(SyncMinecraftCoroutineDispatcher::class)
/**
 * Gets an async minecraft coroutine dispatcher from application
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val Application<*>.asyncMinecraftCoroutineDispatcher: AsyncMinecraftCoroutineDispatcher
    get() = context.getBean(AsyncMinecraftCoroutineDispatcher::class)

/**
 * Gets an async minecraft coroutine dispatcher from current context
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val Application<*>.syncMinecraftCoroutineDispatcher: SyncMinecraftCoroutineDispatcher
    get() = context.getBean(SyncMinecraftCoroutineDispatcher::class)