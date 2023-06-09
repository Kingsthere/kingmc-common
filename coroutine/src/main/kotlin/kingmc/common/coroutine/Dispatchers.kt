package kingmc.common.coroutine

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kotlinx.coroutines.CoroutineDispatcher

/**
 * A coroutine dispatcher from current context
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
@Deprecated("Please use async/sync MinecraftCoroutineDispatcher instead")
val coroutineDispatcher: CoroutineDispatcher
    get() = currentApplication().context.getBean(CoroutineDispatcher::class)

/**
 * Gets an async minecraft coroutine dispatcher from current application
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val asyncMinecraftCoroutineDispatcher: AsyncMinecraftCoroutineDispatcher
    get() = currentApplication().context.getBean(AsyncMinecraftCoroutineDispatcher::class)

/**
 * Gets an async minecraft coroutine dispatcher from current application
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val syncMinecraftCoroutineDispatcher: SyncMinecraftCoroutineDispatcher
    get() = currentApplication().context.getBean(SyncMinecraftCoroutineDispatcher::class)

/**
 * Gets an async minecraft coroutine dispatcher for current application
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val Application.asyncMinecraftCoroutineDispatcher: AsyncMinecraftCoroutineDispatcher
    get() = context.getBean(AsyncMinecraftCoroutineDispatcher::class)

/**
 * Gets an async minecraft coroutine dispatcher for current application
 *
 * @since 0.0.4
 * @author kingsthere
 * @see MinecraftScheduler
 */
@get:WithApplication
val Application.syncMinecraftCoroutineDispatcher: SyncMinecraftCoroutineDispatcher
    get() = context.getBean(SyncMinecraftCoroutineDispatcher::class)