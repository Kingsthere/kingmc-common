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
 * definition is extended from [GenericBeanDefinition]
 *
 * @since 0.0.3
 * @author kingsthere
 */
val BeanDefinition.beanClass: KClass<out Any>
    get() {
        return if (this is GenericBeanDefinition) {
            this.beanClass
        } else {
            throw UnsupportedOperationException()
        }
    }