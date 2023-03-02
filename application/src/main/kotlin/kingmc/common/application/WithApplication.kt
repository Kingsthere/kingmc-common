package kingmc.common.application

/**
 * A marker annotations for marking elements that must run with [Application], codeblocks calling elements
 * that marked must be surround by [application]
 *
 * @since 0.0.5
 * @author kingsthere
 */
@Retention
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.ANNOTATION_CLASS
)
annotation class WithApplication
