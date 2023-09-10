package kingmc.util.annotation.model.classgraph

import io.github.classgraph.AnnotationInfo
import io.github.classgraph.ClassInfo
import kingmc.util.annotation.model.AnnotationContent

/**
 * A superinterface responsible for accessing annotations by ClassGraph
 *
 * @author kingsthere
 * @since 0.0.7
 */
interface ClassGraphAnnotationAccessor {
    /**
     * Create an [AnnotationContent] from specific [annotation]
     */
    fun createContentFor(annotation: AnnotationInfo): AnnotationContent

    /**
     * Create an [AnnotationContent] from specific [annotation] without loading any classes
     */
    fun createContentStaticFor(annotation: AnnotationInfo): AnnotationContent

    /**
     * Check if the given [annotation] have [targetAnnotation] inherited
     *
     * @param annotation annotation to check
     * @param targetAnnotation target annotation to check if this annotation exists in [annotation] specified
     * @return `true` if [targetAnnotation] exists in [annotation]
     */
    fun hasAnnotation(annotation: ClassInfo, targetAnnotation: ClassInfo): Boolean

    /**
     * Check if the given [annotation] have [targetAnnotationClassname] inherited
     *
     * @param annotation annotation to check
     * @param targetAnnotationClassname target annotation to check if this annotation exists in [annotation] specified
     * @return `true` if [targetAnnotationClassname] exists in [annotation]
     */
    fun hasAnnotation(annotation: ClassInfo, targetAnnotationClassname: String): Boolean
}