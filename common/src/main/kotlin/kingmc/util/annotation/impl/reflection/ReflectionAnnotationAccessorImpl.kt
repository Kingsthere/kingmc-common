package kingmc.util.annotation.impl.reflection

import kingmc.util.annotation.RecursiveAnnotationException
import kingmc.util.annotation.impl.IGNORED_ANNOTATIONS
import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationContent
import kingmc.util.annotation.model.reflection.ClassAnnotationNode
import kingmc.util.annotation.model.reflection.ReflectionAnnotationAccessor
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/**
 * A simple [ReflectionAnnotationAccessor] implementation
 */
object ReflectionAnnotationAccessorImpl : ReflectionAnnotationAccessor {
    /**
     * Create an [AnnotationContent] from specific [annotation]
     */
    override fun createContentFor(annotation: Annotation): AnnotationContent {
        return AnnotationContent(generateAnnotationNodeForRootAnnotation(annotation, annotation.annotationClass))
    }

    /**
     * Check if the given [annotation] have [targetAnnotation] inherited
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
            val parentAnnotations = annotation.annotations.filter {
                it.annotationClass.qualifiedName !in IGNORED_ANNOTATIONS
            }
            return parentAnnotations.any {
                hasAnnotation(
                    it.annotationClass,
                    targetAnnotation
                )
            }
        } catch (e: StackOverflowError) {
            throw RecursiveAnnotationException("Recursive annotation found", annotation.qualifiedName ?: "anonymous object")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun generateAnnotationNodeForRootAnnotation(annotation: Annotation, annotationClass: KClass<out Annotation>): ClassAnnotationNode {
        val attributes: MutableList<AnnotationAttribute> = mutableListOf()
        val parentAnnotations = annotationClass.annotations.filter { it.annotationClass.qualifiedName !in IGNORED_ANNOTATIONS }

        // Scan annotation attributes from memberProperties
        annotationClass.memberProperties.forEach { attributeDefinition ->
            val value = (attributeDefinition as KProperty1<Annotation, *>).get(annotation)
            // Add scanned annotation attribute to list
            attributes.add(ClassAnnotationAttributeImpl(annotationClass, attributeDefinition.name, value))
        }

        parentAnnotations.forEach { parentAnnotation ->
            val parentAnnotationNode = generateAnnotationNodeForRootAnnotation(parentAnnotation, parentAnnotation.annotationClass)
            attributes.addAll(parentAnnotationNode.attributes)
        }

        return ClassAnnotationNode(attributes, annotationClass)
    }
}