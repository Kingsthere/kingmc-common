package kingmc.common.context.beans

import kotlin.reflect.KClass

/**
 * A generic implement of [BeanDefinition] exposed the class
 * of the bean
 *
 * @since 0.0.1
 * @author kingsthere
 */
abstract class GenericBeanDefinition(val beanClass: KClass<*>) : AnnotatedBeanDefinition {
    private val implementations: MutableSet<BeanDefinition> = mutableSetOf()

    /**
     * Define a bean implementation to this bean if this
     * bean is an abstract bean
     */
    fun defineImplementation(bean: BeanDefinition) {
        implementations.add(bean)
    }

    override fun implementations(): Set<BeanDefinition> {
        if (isAbstract()) {
            return implementations
        } else {
            throw UnsupportedOperationException("This is not an abstract bean")
        }
    }
}