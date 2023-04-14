package kingmc.common.context

import com.koloboke.collect.map.IntObjMap
import com.koloboke.collect.map.hash.HashIntObjMaps
import com.koloboke.collect.map.hash.HashObjObjMaps
import kingmc.util.KingMCDsl
import kingmc.util.Lifecycle

/**
 * A map define the context by the class loader of the classes
 */
object ContextDefiner {
    val value: MutableMap<Class<*>, IntObjMap<Context>> = HashObjObjMaps.newMutableMap()

    fun runNotifyBeanToObject(context: Context, clazz: Class<*>, block: (Context) -> Any): Any {
        val instance = block(context)
        getOrCreateBeanClassInstanceContexts(clazz).put(instance.hashCode(), context)
        return instance
    }

    fun getOrCreateBeanClassInstanceContexts(clazz: Class<*>): IntObjMap<Context> {
        return value.computeIfAbsent(clazz) { HashIntObjMaps.newMutableMap() }
    }

    fun getContextFor(obj: Any): Context? = value[obj]?.get(obj.hashCode())
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
        return checkNotNull(ContextDefiner.getContextFor(this)) { "No context defined for $this" }
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
