package kingmc.util.format

/**
 * An annotation indicate an object that support the elements
 * in that object supports to format
 *
 * @since 0.0.4
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class EnableFormat
