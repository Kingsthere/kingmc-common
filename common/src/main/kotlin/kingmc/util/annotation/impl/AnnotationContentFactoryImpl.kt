package kingmc.util.annotation.impl

import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationContent
import kingmc.util.annotation.model.AnnotationContentFactory
import kingmc.util.annotation.model.AnnotationNode
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/**
 * A simple [AnnotationContentFactory] implementation
 */
object AnnotationContentFactoryImpl : AnnotationContentFactory {
    /**
     * Create an [AnnotationContent] from specific [annotation]
     */
    override fun invoke(annotation: Annotation): AnnotationContent {
        return AnnotationContent(generateAnnotationNodeForRootAnnotation(annotation, annotation.annotationClass))
    }

    @Suppress("UNCHECKED_CAST")
    private fun generateAnnotationNodeForRootAnnotation(annotation: Annotation, annotationClass: KClass<out Annotation>): AnnotationNode {
        val attributes: MutableList<AnnotationAttribute> = mutableListOf()
        val parentAnnotations = annotationClass.annotations.filter { it.annotationClass.qualifiedName !in IGNORED_ANNOTATIONS }

        // Scan annotation attributes from memberProperties
        annotationClass.memberProperties.forEach { attributeDefinition ->
            val value = (attributeDefinition as KProperty1<Annotation, *>).get(annotation)
            // Add scanned annotation attribute to list
            attributes.add(AnnotationAttributeImpl(annotationClass, attributeDefinition.name, value))
        }

        parentAnnotations.forEach { parentAnnotation ->
            val parentAnnotationNode = generateAnnotationNodeForRootAnnotation(parentAnnotation, parentAnnotation.annotationClass)
            attributes.addAll(parentAnnotationNode.attributes)
        }

        return AnnotationNode(annotationClass, attributes)
    }
}