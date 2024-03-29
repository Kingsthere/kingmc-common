package kingmc.common.context.annotation

import kingmc.common.context.Context
import kingmc.common.context.beans.BeanScope

/**
 * The annotation to specify the scope of this bean, is
 * it prototype or singleton...
 *
 * The `@Scope` annotation is not valid if you add it to the abstract bean declaration, and
 * note that the scope of the implementation bean should be [scope]
 *
 * @author kingsthere
 * @since 0.0.1
 * @see Context
 */
@Target(AnnotationTarget.CLASS)
@Retention
@MustBeDocumented
annotation class Scope(
    /**
     * The scope of this bean
     */
    val scope: BeanScope
)
