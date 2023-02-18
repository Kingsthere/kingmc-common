package kingmc.common.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * An async minecraft coroutine dispatcher
 */
@OptIn(InternalCoroutinesApi::class)
abstract class AsyncMinecraftCoroutineDispatcher : CoroutineDispatcher(), Delay