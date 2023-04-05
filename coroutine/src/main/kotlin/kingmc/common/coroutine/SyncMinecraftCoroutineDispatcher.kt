package kingmc.common.coroutine

import kingmc.common.context.annotation.Component
import kingmc.common.context.annotation.Scope
import kingmc.common.context.beans.BeanScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * A sync minecraft coroutine dispatcher
 */
@OptIn(InternalCoroutinesApi::class)
@Scope(BeanScope.SINGLETON)
@Component
abstract class SyncMinecraftCoroutineDispatcher : CoroutineDispatcher(), Delay