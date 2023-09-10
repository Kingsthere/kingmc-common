package kingmc.common.context

import gnu.trove.map.TIntObjectMap
import gnu.trove.map.hash.TIntObjectHashMap
import kingmc.util.KingMCDsl
import kingmc.util.lifecycle.Lifecycle
import java.util.*

/**
 * A map defines the context by the class loader of the classes
 */
object ContextDefiner {
    val value: MutableMap<Class<*>, TIntObjectMap<Context>> = mutableMapOf()
    val contextNotification: ThreadLocal<Stack<Context>> = ThreadLocal.withInitial { Stack() }

    inline fun runNotifyBeanToObject(context: Context, clazz: Class<*>, block: (Context) -> Any): Any {
        contextNotification.get().push(context)
        val instance = block(context)
        getOrCreateBeanClassInstanceContexts(clazz).put(System.identityHashCode(instance), context)
        contextNotification.get().pop()
        return instance
    }

    fun getOrCreateBeanClassInstanceContexts(clazz: Class<*>): TIntObjectMap<Context> {
        return value.computeIfAbsent(clazz) { TIntObjectHashMap(2) }
    }

    fun getContextFor(obj: Any): Context? = value[obj::class.java]?.get(System.identityHashCode(obj))
}

/**
 * The context of the current object that is instantiating this
 *
 * @author kingsthere
 * @since 0.0.1
 * @return the context this bean is in, throw an exception if this
 *         bean is not injected into any ioc container
 */
@KingMCDsl
val Any.context: Context
    get() {
        val context = ContextDefiner.getContextFor(this)
        return if (context != null) {
            context
        } else {
            val stack = ContextDefiner.contextNotification.get()
            if (stack != null) {
                if (stack.isEmpty()) {
                    throw IllegalStateException("No context defined for $this")
                }
                stack.peek()
            } else {
                throw IllegalStateException("No context defined for $this")
            }
        }
    }

/**
 * The context's lifecycle of this object
 *
 * @author kingsthere
 * @since 0.1.2
 * @return the context this bean is in, throw an exception if this
 *         bean is not injected into any ioc container
 */
@KingMCDsl
val Any.contextLifecycle: Lifecycle
    get() {
        return (context as? LifecycleContext)?.getLifecycle()
            ?: throw UnsupportedOperationException("Current context does not supported for lifecycle")
    }