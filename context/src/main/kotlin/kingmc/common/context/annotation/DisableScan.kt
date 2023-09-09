package kingmc.common.context.annotation

/**
 * Represent to skip this class, the scanner will not scan the class annotated with this
 * annotation even this class is annotated with `Component`
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class DisableScan
