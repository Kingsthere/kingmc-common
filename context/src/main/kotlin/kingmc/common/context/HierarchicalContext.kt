package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition

/**
 * Represent a context that could inherit from other
 * contexts
 *
 * @author kingsthere
 * @since 0.0.1
 */
interface HierarchicalContext : Context {
    /**
     * Gets the protected beans in this context.
     * Protected beans is the beans defined in this context by itself, not inherited from others
     *
     * @author kingsthere
     * @since 0.0.3
     * @return the protected beans
     */
    fun getProtectedBeans(): List<BeanDefinition>

    /**
     * Gets the owning beans in this context, owning beans include
     * protected beans and beans inherited from parent that scoped `SINGLETON` or `PROTOTYPE`
     *
     * Owning beans means the beans that have an isolated instance for every context, and
     * every context is responsible to handle bean instance belongs to that context
     *
     * @author kingsthere
     * @since 0.1.1
     * @return the owning beans
     */
    fun getOwningBeans(): List<BeanDefinition>

    /**
     * Add a parent to this HierarchicalContext
     *
     * @author kingsthere
     * @since 0.0.1
     * @param context the context to inherit from
     */
    fun inheritParent(context: Context)

    /**
     * List all contexts that is inherited from
     * this HierarchicalContext and return as a [Set]
     *
     * @author kingsthere
     * @since 0.0.1
     * @see Set
     * @see Context
     */
    fun listParents(): Set<Context>
}