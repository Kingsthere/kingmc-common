package kingmc.common.context.annotation

import kotlin.reflect.KClass

/**
 * Indicates one or more component classes to import
 *
 * @author kingsthere
 * @since 0.0.2
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention
@MustBeDocumented
@Repeatable
annotation class Import(
    /**
     * The class of the bean to import
     */
    vararg val value: KClass<*>,
)
