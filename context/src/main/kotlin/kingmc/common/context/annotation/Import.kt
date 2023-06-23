package kingmc.common.context.annotation

import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * Indicates one or more component classes to import
 *
 * @since 0.0.2
 * @author kingsthere
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
@Repeatable
@Inherited
annotation class Import(
    /**
     * The class of the bean to import
     */
    vararg val value: KClass<*>,
)
