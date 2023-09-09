package kingmc.common.context.annotation

import kotlin.reflect.KClass
/**
 * A condition to check if the given bean is not present on the ioc container that injecting this bean
 *
 * If the bean to check is an abstract bean, it checks if the bean has an available implementation
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Conditional(ConditionOnBean::class)
annotation class ConditionalOnBeanMissing(
    /**
     * The classes of bean required
     */
    val beanClasses: Array<KClass<out Any>> = [],

    /**
     * The names of bean required
     */
    val beanNames: Array<String> = []
)
