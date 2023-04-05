package kingmc.common.context

import kingmc.common.context.aware.ContextAware
import kingmc.util.KingMCDsl
import kingmc.util.Lifecycle
import java.util.concurrent.ConcurrentHashMap

/**
 * A map define the context by the class loader of the classes
 */
object ContextDefiner : MutableMap<Class<*>, Context> by ConcurrentHashMap() {
    /**
     * A `ThreadLocal` to notify the beans when they instantiated which context created them
     */
    val contextNotification: ThreadLocal<Context> = ThreadLocal()

    @Synchronized
    fun runNotifyBeanToObject(context: Context, clazz: Class<*>, block: (Context) -> Any): Any {
        defineBeanClass(clazz, context)
        contextNotification.set(context)
        val instance = block.invoke(context)
        defineBeanInstance(instance, context)
        contextNotification.remove()
        return instance
    }

    @Synchronized
    fun defineBeanInstance(obj: Any, context: Context) {
        if (obj is ContextAware) {
            obj.context = context
        }
    }

    @Synchronized
    fun defineBeanClass(clazz: Class<*>, context: Context) {
        // So an instantiated bean won't define to a context twice
        if (clazz !in this) {
            put(clazz, context)
        }
    }
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
@get:Synchronized
val Any.context: Context
    get() {
        return if (this is ContextAware) {
            this.context
        } else {
            val notification = ContextDefiner.contextNotification.get()
            if (notification != null) {
                return notification
            } else {
                checkNotNull(ContextDefiner[this::class.java]) { "No context of this(${this::class}) defined" }
            }
        }
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
            ?: throw UnsupportedOperationException("Current context not supported for lifecycle")
    }
