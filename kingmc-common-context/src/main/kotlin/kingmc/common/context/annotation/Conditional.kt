package kingmc.common.context.annotation

import kotlin.reflect.KClass

/**
 * Indicates that the annotated bean is conditional, meaning that the bean is only available under a given condition
 *
 * @since 0.1.2
 * @author kingsthere
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention
@MustBeDocumented
annotation class Conditional(
    /**
     * The condition to test the bean
     */
    val condition: KClass<out Condition>
)
