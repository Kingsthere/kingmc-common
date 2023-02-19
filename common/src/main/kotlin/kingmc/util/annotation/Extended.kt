package kingmc.util.annotation

import kotlin.reflect.KClass

/**
 * Annotation to declare the extended annotations from this annotation
 *
 * @since 0.1
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class Extended(
    /**
     * Parents this annotation to inherit from
     */
    vararg val inheritedParents: KClass<out Annotation>
)
