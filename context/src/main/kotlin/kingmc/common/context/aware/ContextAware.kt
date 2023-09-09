package kingmc.common.context.aware

import kingmc.common.context.Context

/**
 * An interface indicating that beans is aware of what context instantiate them
 *
 * @author kingsthere
 * @since 0.0.5
 */
interface ContextAware {
    /**
     * Set the context to this bean
     */
    fun setContext(context: Context)
}