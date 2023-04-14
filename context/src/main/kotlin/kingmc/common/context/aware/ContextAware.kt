package kingmc.common.context.aware

import kingmc.common.context.Context

/**
 * A marker interface indicating that beans is aware of what context
 * instantiate them, add this to a bean if **this bean will instantiate more than once**
 *
 * @since 0.0.5
 * @author kingsthere
 */
interface ContextAware {
    /**
     * Set the context to this bean
     */
    fun setContext(context: Context)
}