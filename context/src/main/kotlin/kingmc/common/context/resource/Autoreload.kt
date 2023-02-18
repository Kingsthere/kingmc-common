package kingmc.common.context.resource

/**
 * Mark a resource that is autoreloaded, when the file change in disk
 * it automatically reloaded into memory
 *
 * @since 0.0.5
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS)
annotation class Autoreload
