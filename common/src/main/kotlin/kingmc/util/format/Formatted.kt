package kingmc.util.format

/**
 * An annotation indicates an object that supports the elements
 * in that object supports to format
 *
 * @author kingsthere
 * @since 0.0.4
 */
@Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class Formatted
