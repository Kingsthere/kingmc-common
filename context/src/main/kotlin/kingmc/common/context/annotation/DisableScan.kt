package kingmc.common.context.annotation

import java.lang.annotation.Inherited

/**
 * Represent to skip this class, the scanner will not scan the class annotated with this
 * annotation even this class is annotated with `Component`
 *
 * @since 0.0.1
 * @author kingsthere
 * @see Context
 */
@Retention
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Inherited
annotation class DisableScan
