package kingmc.util.annotation.impl

import kingmc.util.annotation.RecursiveAnnotationException
import kingmc.util.annotation.model.AnnotationTreeFactory
import kotlin.reflect.KClass

/**
 * A simple [AnnotationTreeFactory] implementation
 */
object AnnotationTreeFactoryImpl : AnnotationTreeFactory {
    /**
     * Check if specific [annotation] have [targetAnnotation] inherited
     *
     * @param annotation annotation to check
     * @param targetAnnotation target annotation to check if this annotation exists in [annotation] specified
     * @return `true` if [targetAnnotation] exists in [annotation]
     */
    override fun hasAnnotation(annotation: KClass<out Annotation>, targetAnnotation: KClass<out Annotation>): Boolean {
        try {
            if (annotation == targetAnnotation) {
                return true
            }
            val parentAnnotations = annotation.annotations.filter { it.annotationClass.qualifiedName !in IGNORED_ANNOTATIONS }
            return parentAnnotations.any { hasAnnotation(it.annotationClass, targetAnnotation) }
        } catch (e: StackOverflowError) {
            throw RecursiveAnnotationException("Recursive annotation found", annotation)
        }
    }
}