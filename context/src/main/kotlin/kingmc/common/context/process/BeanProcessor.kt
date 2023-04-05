package kingmc.common.context.process

import kingmc.common.context.Context
import kingmc.util.Lifecycle

/**
 * A BeanLoader to process bean when a bean is loaded into an [Context],
 * notice that the bean processors must be defined as a **singleton bean**
 * otherwise the bean processor won't work
 *
 * @since 0.0.2
 * @author kingsthere
 * @see Context
 */
interface BeanProcessor {
    /**
     * The lifecycle where this processor start to work, this
     * only work when you use it to process a [LifecycleContext]
     *
     * @see Lifecycle
     */
    val lifecycle: Int

    /**
     * The priority of this processor to process beans
     */
    val priority: Byte
        get() = DEFAULT_PRIORITY

    /**
     * Process a bean
     *
     * @param bean the bean to process
     * @param context the container where the bean is processing
     * @see Context
     */
    fun process(context: Context, bean: Any): Boolean { return false }

    /**
     * After processing, called **after** [process] all the beans through
     * a context
     */
    fun afterProcess(context: Context) {  }

    /**
     * Called when a bean removed from context
     *
     * @param bean the bean to process
     * @param context the container where the bean is processing
     * @see Context
     */
    fun dispose(context: Context, bean: Any) {  }

    /**
     * Called after [dispose] all beans through a context
     */
    fun afterDispose(context: Context) {  }

    companion object {
        /**
         * The default priority of bean processor
         */
        const val DEFAULT_PRIORITY: Byte = 0
    }
}