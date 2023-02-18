package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.beans.depends.DependencyDescriptor
import kingmc.util.errorprone.Immutable

/**
 * A BeanDefinition describes an instance of a Bean that contains property
 * values, constructor parameter values, and more implementation information
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Immutable
interface BeanDefinition {
    /**
     * The name of this bean
     *
     *
     * **Note:** The name of beans must _unique_ in a context
     *
     * @since 0.0.1
     */
    val name: String

    /**
     * The context that loads this bean definition
     *
     * @since 0.0.4
     */
    val context: Context

    /**
     * The dependencies of this bean
     *
     * @since 0.0.1
     */
    val dependencies: DependencyDescriptor

    /**
     * The scope of this bean
     *
     * @since 0.0.2
     */
    val scope: BeanScope

    /**
     * Return `true` if this bean is a singleton
     */
    fun isSingleton(): Boolean

    /**
     * Return `true` if this bean is a prototype
     */
    fun isPrototype(): Boolean

    /**
     * Return `true` if this bean is an abstract bean
     */
    fun isAbstract(): Boolean

    /**
     * Return the implementation beans of this bean definition
     * if this bean is an abstract bean ([isAbstract])
     */
    fun implementations(): Set<BeanDefinition>
}