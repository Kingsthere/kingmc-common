package kingmc.common.context.annotation

import kotlin.reflect.KClass

/**
 * A condition to check if all class is present in the current bean environment
 *
 * @author kingsthere
 * @since 0.1.1
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Conditional(ConditionOnClass::class)
annotation class ConditionalOnClass(
    /**
     * Classname of classes that must present
     *
     * @since 0.1.1
     */
    val classnames: Array<String> = [],

    /**
     * classes that must present
     *
     * @since 0.1.1
     */
    val classes: Array<KClass<*>> = []
)
