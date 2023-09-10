package kingmc.common.context.annotation

import kingmc.common.context.Context

/**
 * The main annotation for accessing beans defined in ioc container
 *
 *
 * The fields that are annotated with [Autowired]
 * will set to the specified bean got from
 * [Context]
 *
 * @see Context
 * @author kingsthere
 * @since 0.0.1
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR)
@Retention
@MustBeDocumented
annotation class Autowired(
    /**
     * Declares whether the annotated dependency is required, set to
     * false is this dependency is optional, if the bean need to
     * autowire is not found then it will just skip
     *
     *
     * Defaults to `true`.
     *
     * @since 0.0.1
     */
    val required: Boolean = true
)
