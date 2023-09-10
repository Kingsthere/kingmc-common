package kingmc.util.annotation.model.reflection

import kingmc.util.annotation.model.AnnotationContent
import kotlin.reflect.KClass

/**
 * A superinterface responsible for accessing annotations by reflection
 *
 * @author kingsthere
 * @since 0.0.7
 */
interface ReflectionAnnotationAccessor {
    /**
     * Create an [AnnotationContent] from specific [annotation]
     */
    fun createContentFor(annotation: Annotation): AnnotationContent

    /**
     * Check if the given [annotation] have [targetAnnotation] inherited
     *
     * @param annotation annotation to check
     * @param targetAnnotation target annotation to check if this annotation exists in [annotation] specified
     * @return `true` if [targetAnnotation] exists in [annotation]
     */
    fun hasAnnotation(annotation: KClass<out Annotation>, targetAnnotation: KClass<out Annotation>): Boolean
}