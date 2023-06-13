package kingmc.common.context.beans

import kotlin.reflect.KClass

/**
 * Get the annotations defined in this bean if this bean
 * definition is extended from [AnnotatedBeanDefinition]
 *
 * @since 0.0.3
 * @author kingsthere
 */
val BeanDefinition.annotations: List<Annotation>
    get() {
        return if (this is AnnotatedBeanDefinition) {
            this.annotations
        } else {
            emptyList()
        }
    }

/**
 * Get the bean class defined in this bean if this bean
 * definition is extended from [ClassBeanDefinition]
 *
 * @since 0.0.3
 * @author kingsthere
 */
val BeanDefinition.beanClass: KClass<out Any>
    get() {
        return if (this is ClassBeanDefinition) {
            this.beanClass
        } else {
            throw UnsupportedOperationException()
        }
    }

/**
 * Get the lifecycle of this bean definition
 *
 * @since 0.0.9
 * @author kingsthere
 */
val BeanDefinition.lifecycle: Int
    get() {
        return if (this is LateinitBeanDefinition) {
            this.lifecycle
        } else {
            0
        }
    }