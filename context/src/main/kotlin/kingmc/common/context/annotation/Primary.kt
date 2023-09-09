package kingmc.common.context.annotation

import kingmc.common.context.Context

/**
 * To indicate that a bean is `primary`
 *
 *
 * If the bean is indicated with `primary`, the context will prefer the `primary` beans
 * when getting beans from this context
 *
 * @author kingsthere
 * @since 0.0.7
 * @see Context
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention
@MustBeDocumented
annotation class Primary
