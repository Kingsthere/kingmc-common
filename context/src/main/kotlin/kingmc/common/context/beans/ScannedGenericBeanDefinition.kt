package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.annotation.Component
import kingmc.common.context.beans.depends.DependencyDescriptor
import kotlin.reflect.KClass

/**
 * Implement of [BeanDefinition] represent a registered
 * bean from a class that annotated with [Component]
 *
 * @since 0.0.1
 * @author kingsthere
 */
class ScannedGenericBeanDefinition(
    beanClass: KClass<*>,
    override val context: Context,
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

        other as ScannedGenericBeanDefinition

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "ScannedGenericBeanDefinition(name='$name',beanClass='$beanClass',isAbstract=$isAbstract,scope=$scope)"
    }
}