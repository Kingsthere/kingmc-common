package kingmc.common.context.annotation

/**
 * A condition to check if a property of the context is equals to the value
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Conditional(ConditionOnProperty::class)
annotation class ConditionalOnProperty(
    /**
     * The property to check
     */
    val property: String,

    /**
     * The value to check to the property
     */
    val value: String = "true"
)
