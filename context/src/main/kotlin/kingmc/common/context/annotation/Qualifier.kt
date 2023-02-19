package kingmc.common.context.annotation

import kingmc.util.annotation.Extendable

/**
 * Use this annotation to present the name
 * of a bean to autowired a bean correctly from the container
 *
 *
 * The fields that annotated with [Qualifier] should also annotate
 * with [Autowired]
 *
 * @see Component
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
@Extendable
annotation class Qualifier(
    /**
     * The name of the bean to acquire bean from
     */
    val value: String
)
