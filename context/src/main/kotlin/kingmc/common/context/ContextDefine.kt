package kingmc.common.context

import kingmc.common.context.aware.ContextAware
import kingmc.util.KingMCDsl
import kingmc.util.Lifecycle
import java.util.concurrent.ConcurrentHashMap

/**
 * A map define the context by the class loader of the classes
 */
object ContextDefiner : MutableMap<Class<*>, Context> by ConcurrentHashMap() {
    fun defineBeanInstance(obj: Any, context: Context) {
        if (obj is ContextAware) {
            obj.context = context
        }
    }

    fun defineBeanClass(obj: Class<*>, context: Context) {
        // So an instantiated bean won't define to a context twice
        if (obj !in this) {
            put(obj, context)
        }
    }
}

/**
 * The context of current object that is injected to
 *
 * @since 0.0.1
 * @author kingsthere
 * @return the context this bean is in, throw an exception if this
 *         bean is not injected into any ioc container
 */
@KingMCDsl
val Any.context: Context
    get() {
        return if (this is ContextAware) {
            this.context
        } else {
            ContextDefiner[this::class.java] ?: throw UnsupportedOperationException()
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
