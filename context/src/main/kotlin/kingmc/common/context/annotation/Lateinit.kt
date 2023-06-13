package kingmc.common.context.annotation

/**
 * An annotation to mark the lifecycle that the annotated element depends on
 *
 * Add to a [AnnotationTarget.CLASS] so that class won't be loaded by a classloader to prevent
 * static code blocks being executed when dependencies aren't loaded
 *
 * Add to a [AnnotationTarget.FIELD] to a field that requires dependencies injected
 * tp it so the process of injecting dependencies to that field will postpone until the
 * specified lifecycle
 *
 * @since 0.0.9
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class Lateinit(
    /**
     * The lifecycle to specify when the class will be loaded
     */
    val lifecycle: Int
)
