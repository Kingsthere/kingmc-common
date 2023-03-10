package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.beans.depends.DependencyDescriptor
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * Implement of [BeanDefinition] represent a registered
 * bean from a configuration class
 *
 * @since 0.0.1
 * @author kingsthere
 */
class AnnotatedGenericBeanDefinition(
    beanClass: KClass<*>,
    override val context: Context,
    val configurationBean: BeanDefinition,
    val beanProvider: KFunction<*>,
    override val annotations: List<Annotation>,
    override val name: String,
    override val dependencies: DependencyDescriptor,
    override val scope: BeanScope,
    private val isAbstract: Boolean
) : GenericBeanDefinition(beanClass) {

    /**
     * Return `true` if this bean is a singleton
     */
    override fun isSingleton(): Boolean =
        scope != BeanScope.PROTOTYPE && !isAbstract()

    /**
     * Return `true` if this bean is a prototype
     */
    override fun isPrototype(): Boolean =
        scope == BeanScope.PROTOTYPE

    /**
     * Return `true` if this bean is an abstract bean
     */
    override fun isAbstract(): Boolean =
        isAbstract

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnnotatedGenericBeanDefinition

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "AnnotatedGenericBeanDefinition(name='$name',configurationBean='$beanClass',beanProvider='$beanProvider')"
    }
}