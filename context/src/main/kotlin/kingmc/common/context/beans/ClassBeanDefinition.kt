package kingmc.common.context.beans

import java.util.*
import kotlin.reflect.KClass

/**
 * An abstract implement of [BeanDefinition] exposed the class
 * of the bean
 *
 * @since 0.0.1
 * @author kingsthere
 */
abstract class ClassBeanDefinition(val beanClass: KClass<*>) : AnnotatedBeanDefinition {
    private val implementations: MutableSet<BeanDefinition> = TreeSet(compareByDescending { it.priority })

    /**
     * Define a bean implementation to this bean if this
     * bean is an abstract bean
     */
    fun defineImplementation(bean: BeanDefinition) {
        implementations.add(bean)
    }

    override fun implementations(): Collection<BeanDefinition> {
        if (isAbstract()) {
            return implementations
        } else {
            throw UnsupportedOperationException("This is not an abstract bean")
        }
    }
}