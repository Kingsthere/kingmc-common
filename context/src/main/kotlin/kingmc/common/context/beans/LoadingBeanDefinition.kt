package kingmc.common.context.beans

import kingmc.common.context.Context

/**
 * Represents a loading bean definition
 *
 * @author kingsthere
 * @since 0.1.1
 */
interface LoadingBeanDefinition {
    /**
     * Returns the name of this bean
     */
    val name: String

    /**
     * Returns the scope of this bean
     */
    val scope: BeanScope

    /**
     * Returns the bean privacy of this bean
     */
    val privacy: BeanPrivacy

    /**
     * Returns `true` if this bean is deprecated
     */
    val deprecated: Boolean

    /**
     * Returns `true` if this bean is primary
     */
    val primary: Boolean

    /**
     * Returns the priority of this bean definition
     */
    val priority: Byte

    /**
     * Returns `true` if this is an open bean
     */
    fun isOpen(): Boolean

    /**
     * Returns `true` if this is an abstract bean
     */
    fun isAbstract(): Boolean

    /**
     * Return the implementation beans of this bean definition if this bean is an abstract bean
     */
    fun implementations(): Collection<LoadingBeanDefinition>

    /**
     * Finish loading this bean definition and create a [BeanDefinition] from this loading
     * bean definition
     */
    fun finishLoading(context: Context): BeanDefinition
}