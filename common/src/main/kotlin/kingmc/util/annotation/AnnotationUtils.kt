package kingmc.util.annotation

import kingmc.util.annotation.impl.GenericAnnotationNode
import kingmc.util.annotation.impl.cglib.CGLIBAnnotationMocker
import kingmc.util.annotation.impl.cglib.CGLIBAnnotationNodeFactory
import kingmc.util.annotation.model.AnnotationNode
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

val DEFAULT_ANNOTATION_FACTORY = CGLIBAnnotationNodeFactory

/**
 * Check if this node contains a [annotationClass] which is inherited
 */
operator fun AnnotationNode.contains(annotationClass: KClass<out Annotation>): Boolean {
    if ((this as GenericAnnotationNode).annotationClass == annotationClass) {
        return true
    }

    if (this.inherited.isNotEmpty()) {
        return this.inherited.any { annotationClass in it }
    }

    return false
}

/**
 * Check if this class have an annotation declared
 *
 * @since 0.1
 */
inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation() =
    annotations.hasAnnotationClass(T::class)

/**
 * Check if this class have an annotation declared
 *
 * @since 0.1
 */
inline fun <reified T : Annotation> List<Annotation>.hasAnnotation() =
    hasAnnotationClass(T::class)

/**
 * Check if this class have an annotation declared by class
 *
 * @since 0.1
 */
fun List<Annotation>.hasAnnotationClass(annotationClass: KClass<out Annotation>): Boolean {
    return any { annotation ->
        val annotationNode = kingmc.util.annotation.DEFAULT_ANNOTATION_FACTORY(annotation.annotationClass)
        annotationClass in annotationNode
    }
}

/**
 * Get an annotation from this class by the type of the annotation
 *
 * @since 0.1
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotation() =
    annotations.getAnnotationClass(T::class) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 *
 * @since 0.1
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotations(): List<T> =
    annotations.getAnnotationsClass(T::class) as List<T>


/**
 * Get an annotation from this list of annotations by the type of the annotation
 *
 * @since 0.1
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotation() =
    getAnnotationClass(T::class) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 *
 * @since 0.1
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotations(): List<T> =
    getAnnotationsClass(T::class) as List<T>
/**
 * Get an annotation from this class by the type of the annotation
 * by class
 *
 * @since 0.1
 */
fun List<Annotation>.getAnnotationClass(annotationClass: KClass<out Annotation>): Annotation? {
    val annotation = find { annotationClass in kingmc.util.annotation.DEFAULT_ANNOTATION_FACTORY(it.annotationClass) } ?: return null
    val annotationNode = kingmc.util.annotation.DEFAULT_ANNOTATION_FACTORY(annotationClass)
    return CGLIBAnnotationMocker(annotation, annotationNode as GenericAnnotationNode)
}

/**
 * Get multiple annotations from this class by the type of the annotation
 * by class
 *
 * @since 0.1
 */
fun List<Annotation>.getAnnotationsClass(annotationClass: KClass<out Annotation>): List<Annotation> {
    return filter { annotationClass in kingmc.util.annotation.DEFAULT_ANNOTATION_FACTORY(it.annotationClass) }
        .map {
            val annotationNode = kingmc.util.annotation.DEFAULT_ANNOTATION_FACTORY(annotationClass)
            CGLIBAnnotationMocker(it, annotationNode as GenericAnnotationNode)
        }
}