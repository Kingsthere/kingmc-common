package kingmc.common.context.condition

/**
 * A condition to check if a class is missing in current bean environment
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ConditionalOnClassMissing(
    /**
     * Name of classes must miss
     *
     * @since 0.0.1
     */
    vararg val value: String
)
