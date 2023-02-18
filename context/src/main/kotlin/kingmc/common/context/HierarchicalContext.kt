package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition

/**
 * Represent a context that could inherit from other
 * contexts
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface HierarchicalContext : Context {
    /**
     * Gets the protected beans in this context, protected beans is
     * the beans defined in this context by itself, not inherit from
     * others
     *
     * @since 0.0.3
     * @author kingsthere
     * @return the protected beans
     */
    fun getProtectedBeans(): List<BeanDefinition>

    /**
     * Gets the owning beans in this context, owning beans include
     * protected beans and beans inherited from parent that scoped `SINGLETON` or `PROTOTYPE`
     *
     * @since 0.0.5
     * @author kingsthere
     * @return the owning beans
     */
    fun getOwningBeans(): List<BeanDefinition>

    /**
     * Add a parent to this HierarchicalContext
     *
     * @since 0.0.1
     * @author kingsthere
     * @param context the context to inherit from
     */
    fun inheritParent(context: Context)

    /**
     * List all contexts that is inherited from
     * this HierarchicalContext and return as a [Set]
     *
     * @since 0.0.1
     * @author kingsthere
     * @see Set
     * @see Context
     */
    fun listParents(): Set<Context>
}