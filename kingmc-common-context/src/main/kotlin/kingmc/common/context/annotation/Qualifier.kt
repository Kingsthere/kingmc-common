package kingmc.common.context.annotation

/**
 * Use this annotation to present the name
 * of a bean to autowired a bean correctly from the container
 *
 *
 * The fields that annotated with [Qualifier] should also annotate
 * with [Autowired]
 *
 * @author kingsthere
 * @since 0.0.1
 * @see Component
 */
@Retention
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class Qualifier(
    /**
     * The name of the bean to acquire bean from
     */
    val value: String
)
