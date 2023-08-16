package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.annotation.Component
import kotlin.reflect.KClass

/**
 * Implement of [BeanDefinition] represent a registered
 * bean from a class that annotated with [Component]
 *
 * @since 0.0.1
 * @author kingsthere
 */
open class ScannedGenericBeanDefinition(
    beanClass: KClass<*>,
    override val context: Context,
    override val annotations: List<Annotation>,
    override val name: String,
    override val scope: BeanScope,
    private val isAbstract: Boolean,
    override val deprecated: Boolean,
    override val primary: Boolean,
    override val privacy: BeanPrivacy,
    override val priority: Byte
) : ClassBeanDefinition(beanClass) {
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

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "ScannedGenericBeanDefinition(name='$name',beanClass='$beanClass',isAbstract=$isAbstract,scope=$scope)"
    }
}