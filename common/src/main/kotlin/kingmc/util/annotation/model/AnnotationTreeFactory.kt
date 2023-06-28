package kingmc.util.annotation.model

import kotlin.reflect.KClass

/**
 * Factory superinterface for checking annotations as a tree structure data object
 *
 * @since 0.1.0
 * @author kingsthere
 */
interface AnnotationTreeFactory {
    /**
     * Check if specific [annotation] have [targetAnnotation] inherited
     *
     * @param annotation annotation to check
     * @param targetAnnotation target annotation to check if this annotation exists in [annotation] specified
     * @return `true` if [targetAnnotation] exists in [annotation]
     */
    fun hasAnnotation(annotation: KClass<out Annotation>, targetAnnotation: KClass<out Annotation>): Boolean
}