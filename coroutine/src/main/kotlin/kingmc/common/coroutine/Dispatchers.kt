package kingmc.common.coroutine

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kingmc.common.context.getBeanOrThrow
import kotlinx.coroutines.CoroutineDispatcher

/**
 * A coroutine dispatcher from current context
 *
 * @author kingsthere
 * @since 0.0.4
 */
@get:WithApplication
@Deprecated("Please use async/sync MinecraftCoroutineDispatcher instead")
val coroutineDispatcher: CoroutineDispatcher
    get() = currentApplication().context.getBeanOrThrow<CoroutineDispatcher>()

/**
 * Gets an async minecraft coroutine dispatcher from the current application
 *
 * @author kingsthere
 * @since 0.0.4
 */
@get:WithApplication
val asyncMinecraftCoroutineDispatcher: AsyncMinecraftCoroutineDispatcher
    get() = currentApplication().context.getBeanOrThrow<AsyncMinecraftCoroutineDispatcher>()

/**
 * Gets an async minecraft coroutine dispatcher from current application
 *
 * @author kingsthere
 * @since 0.0.4
 */
@get:WithApplication
val syncMinecraftCoroutineDispatcher: SyncMinecraftCoroutineDispatcher
    get() = currentApplication().context.getBeanOrThrow<SyncMinecraftCoroutineDispatcher>()

/**
 * Gets an async minecraft coroutine dispatcher for current application
 *
 * @author kingsthere
 * @since 0.0.4
 */
@get:WithApplication
val Application.asyncMinecraftCoroutineDispatcher: AsyncMinecraftCoroutineDispatcher
    get() = context.getBeanOrThrow<AsyncMinecraftCoroutineDispatcher>()

/**
 * Gets an async minecraft coroutine dispatcher for current application
 *
 * @author kingsthere
 * @since 0.0.4
 */
@get:WithApplication
val Application.syncMinecraftCoroutineDispatcher: SyncMinecraftCoroutineDispatcher
    get() = context.getBeanOrThrow<SyncMinecraftCoroutineDispatcher>()