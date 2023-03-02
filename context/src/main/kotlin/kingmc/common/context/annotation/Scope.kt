package kingmc.common.context.annotation

import kingmc.common.context.Context
import kingmc.common.context.beans.BeanScope
import kingmc.util.annotation.Extendable
import java.lang.annotation.Inherited

/**
 * The annotation to specify the scope of this bean, is
 * it prototype or singleton...
 *
 * The `@Scope` annotation is not valid if you add it to the abstract bean declaration, and
 * note that the scope of the implementation bean should be [value]
 *
 * @see Context
 * @since 0.0.1
 * @author kingsthere
 */
@Target(AnnotationTarget.CLASS)
@Retention
@MustBeDocumented
@Inherited
@Extendable
annotation class Scope(
    /**
     * The scope of this bean
     */
    val value: BeanScope
)
