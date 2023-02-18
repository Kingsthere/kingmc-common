package kingmc.common.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * An sync minecraft coroutine dispatcher
 */
@OptIn(InternalCoroutinesApi::class)
abstract class SyncMinecraftCoroutineDispatcher : CoroutineDispatcher(), Delay