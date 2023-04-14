package kingmc.common.context.condition

import kingmc.util.format.Formatted

/**
 * A condition to check if a property of the context is equals to the value
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ConditionalOnProperty(
    /**
     * The property to check
     */
    @Formatted
    val property: String,

    /**
     * The value to check to the property
     */
    @Formatted
    val value: String = "true"
)
