package kingmc.common.context.annotation

/**
 * An annotation to mark the lifecycle that the annotated element depends on and that element
 * will only available at specified [lifecycle]
 *
 * Add to a [AnnotationTarget.CLASS] so that class won't be loaded by a classloader to prevent
 * static code blocks being executed when dependencies aren't loaded
 *
 * Add to a [AnnotationTarget.PROPERTY] to a property that requires dependencies injected
 * tp it so the process of injecting dependencies to that field will postpone until the
 * specified lifecycle
 *
 * @since 0.0.9
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class Lateinit(
    /**
     * The lifecycle to specify when the class will be loaded
     */
    val lifecycle: Int
)
