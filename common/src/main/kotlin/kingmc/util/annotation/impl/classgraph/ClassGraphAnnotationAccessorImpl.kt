package kingmc.util.annotation.impl.classgraph

import io.github.classgraph.AnnotationInfo
import io.github.classgraph.ClassInfo
import kingmc.util.annotation.RecursiveAnnotationException
import kingmc.util.annotation.impl.IGNORED_ANNOTATIONS
import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationContent
import kingmc.util.annotation.model.classgraph.ClassGraphAnnotationAccessor
import kingmc.util.annotation.model.classgraph.ClassGraphAnnotationNode

/**
 * A simple [ClassGraphAnnotationAccessor] implementation
 */
object ClassGraphAnnotationAccessorImpl : ClassGraphAnnotationAccessor {
    /**
     * Create an [AnnotationContent] from specific [annotation]
     */
    override fun createContentFor(annotation: AnnotationInfo): AnnotationContent {
        return AnnotationContent(generateAnnotationNodeForRootAnnotation(annotation, annotation.classInfo))
    }

    /**
     * Create an [AnnotationContent] from specific [annotation] without loading any classes
     */
    override fun createContentStaticFor(annotation: AnnotationInfo): AnnotationContent {
        return AnnotationContent(generateAnnotationNodeForRootAnnotationStatic(annotation, annotation.classInfo))
    }

    /**
     * Check if the given [annotation] have [targetAnnotation] inherited
     *
     * @param annotation annotation to check
     * @param targetAnnotation target annotation to check if this annotation exists in [annotation] specified
     * @return `true` if [targetAnnotation] exists in [annotation]
     */
    override fun hasAnnotation(annotation: ClassInfo, targetAnnotation: ClassInfo): Boolean {
        try {
            if (annotation == targetAnnotation) {
                return true
            }
            val parentAnnotations = annotation.annotations.filter { it.name !in IGNORED_ANNOTATIONS }
            return parentAnnotations.any {
                hasAnnotation(
                    it,
                    targetAnnotation
                )
            }
        } catch (e: StackOverflowError) {
            throw RecursiveAnnotationException("Recursive annotation found", annotation.fullyQualifiedDefiningMethodName)
        }
    }

    override fun hasAnnotation(annotation: ClassInfo, targetAnnotationClassname: String): Boolean {
        try {
            if (annotation.name == targetAnnotationClassname) {
                return true
            }
            val parentAnnotations = annotation.annotations.filter { it.name !in IGNORED_ANNOTATIONS }
            return parentAnnotations.any {
                hasAnnotation(
                    it,
                    targetAnnotationClassname
                )
            }
        } catch (e: StackOverflowError) {
            throw RecursiveAnnotationException("Recursive annotation found", annotation.name)
        }
    }

    private fun generateAnnotationNodeForRootAnnotation(annotation: AnnotationInfo, annotationClass: ClassInfo): ClassGraphAnnotationNode {
        val attributes: MutableList<AnnotationAttribute> = mutableListOf()
        val parentAnnotations = annotationClass.annotationInfo.filter { it.classInfo.name !in IGNORED_ANNOTATIONS }

        // Scan annotation attributes from AnnotationInfo
        annotation.parameterValues.forEach { parameterValue ->
            // Add scanned annotation attribute to list
            attributes.add(ClassGraphAnnotationAttributeImpl(annotationClass, parameterValue.name, parameterValue.value))
        }

        parentAnnotations.forEach { parentAnnotation ->
            val parentAnnotationNode = generateAnnotationNodeForRootAnnotation(parentAnnotation, parentAnnotation.classInfo)
            attributes.addAll(parentAnnotationNode.attributes)
        }

        return ClassGraphAnnotationNode(attributes, annotationClass)
    }

    private fun generateAnnotationNodeForRootAnnotationStatic(annotation: AnnotationInfo, annotationClass: ClassInfo): ClassGraphAnnotationNode {
        val attributes: MutableList<AnnotationAttribute> = mutableListOf()
        val parentAnnotations = annotationClass.annotationInfo.filter { it.classInfo.name !in IGNORED_ANNOTATIONS }

        // Scan annotation attributes from AnnotationInfo
        annotation.parameterValues.forEach { parameterValue ->
            // Add scanned annotation attribute to list
            attributes.add(StaticClassGraphAnnotationAttribute(annotationClass, parameterValue.name, parameterValue.value))
        }

        parentAnnotations.forEach { parentAnnotation ->
            val parentAnnotationNode = generateAnnotationNodeForRootAnnotationStatic(parentAnnotation, parentAnnotation.classInfo)
            attributes.addAll(parentAnnotationNode.attributes)
        }

        return ClassGraphAnnotationNode(attributes, annotationClass)
    }
}