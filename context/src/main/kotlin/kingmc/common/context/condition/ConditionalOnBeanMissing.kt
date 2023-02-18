package kingmc.common.context.condition

import kotlin.reflect.KClass

/**
 * A condition to check if a bean is not available on
 * the ioc container that injecting this bean
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ConditionalOnBeanMissing(
    /**
     * The bean class
     *
     * @since 0.0.1
     */
    val beanClass: KClass<out Any> = Any::class,

    /**
     * The qualified bean name
     */
    val beanName: String = ""
)
