package kingmc.common.context.annotation

import kingmc.util.annotation.Extendable
import kingmc.common.context.Context
import kingmc.common.context.beans.BeanScope
import java.lang.annotation.Inherited

/**
 * The annotation to specify the scope of this bean, is
 * it prototype or singleton...
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
