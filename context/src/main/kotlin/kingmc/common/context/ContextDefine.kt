package kingmc.common.context

import kingmc.util.KingMCDsl
import kingmc.util.Lifecycle

/**
 * A map define the context by the class loader of the classes
 */
object ContextDefiner {
    val value: MutableMap<Class<*>, MutableMap<Int, Context>> = mutableMapOf()
    val contextNotification: ThreadLocal<Context> = ThreadLocal()

    fun runNotifyBeanToObject(context: Context, clazz: Class<*>, block: (Context) -> Any): Any {
        contextNotification.set(context)
        val instance = block(context)
        getOrCreateBeanClassInstanceContexts(clazz)[System.identityHashCode(instance)] = context
        contextNotification.remove()
        return instance
    }

    fun getOrCreateBeanClassInstanceContexts(clazz: Class<*>): MutableMap<Int, Context> {
        return value.computeIfAbsent(clazz) { HashMap() }
    }

    fun getContextFor(obj: Any): Context? = value[obj::class.java]?.get(System.identityHashCode(obj))
}

/**
 * The context of current object that is instantiating this
 *
 * @since 0.0.1
 * @author kingsthere
 * @return the context this bean is in, throw an exception if this
 *         bean is not injected into any ioc container
 */
@KingMCDsl
val Any.context: Context
    get() {
        return checkNotNull(ContextDefiner.getContextFor(this) ?: ContextDefiner.contextNotification.get()) { "No context defined for $this" }
    }

/**
 * The lifecycle of this object's context is running
 *
 * @since 0.0.1
 * @author kingsthere
 * @return the context this bean is in, throw an exception if this
 *         bean is not injected into any ioc container
 */
@KingMCDsl
val Any.contextLifecycle: Lifecycle<Runnable>
    get() {
        return (context as? LifecycleContext)?.lifecycle()
            ?: throw UnsupportedOperationException("Current context does not supported for lifecycle")
    }
