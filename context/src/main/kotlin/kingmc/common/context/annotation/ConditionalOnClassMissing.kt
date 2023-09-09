package kingmc.common.context.annotation

import kotlin.reflect.KClass

/**
 * A condition to check if all class is missing in the current bean environment
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Conditional(ConditionOnClassMissing::class)
annotation class ConditionalOnClassMissing(
    /**
     * Classname of classes that must not present
     *
     * @since 0.1.1
     */
    val classnames: Array<String> = [],

    /**
     * classes that must not present
     *
     * @since 0.1.1
     */
    val classes: Array<KClass<*>> = []
)
