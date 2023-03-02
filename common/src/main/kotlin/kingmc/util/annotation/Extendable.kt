package kingmc.util.annotation

/**
 * Declare an annotation class or an annotation attribute is **extendable**, if
 * an annotation is **extendable** then it can be inherited by [Extended] annotation
 * to inherit attributes from parent annotation
 *
 * @since 0.0.7
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.PROPERTY_GETTER)
annotation class Extendable
