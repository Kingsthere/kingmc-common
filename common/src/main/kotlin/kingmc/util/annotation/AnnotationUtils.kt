package kingmc.util.annotation

import kingmc.util.annotation.impl.GenericAnnotationNode
import kingmc.util.annotation.impl.cglib.CGLIBAnnotationEnhancer
import kingmc.util.annotation.impl.cglib.CGLIBAnnotationNodeFactory
import kingmc.util.annotation.model.AnnotationNode
import kingmc.util.annotation.model.AnnotationNodeFactory
import kingmc.util.format.BracketStyle
import kingmc.util.format.FormatContext
import kingmc.util.format.FormatStyle
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

val DEFAULT_ANNOTATION_ENHANCER: AnnotationEnhancer = CGLIBAnnotationEnhancer
val DEFAULT_ANNOTATION_FACTORY: AnnotationNodeFactory = CGLIBAnnotationNodeFactory

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
 * @since 0.0.7
 */
inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation() =
    annotations.hasAnnotationClass(T::class)

/**
 * Check if this class have an annotation declared
 *
 * @since 0.0.7
 */
inline fun <reified T : Annotation> List<Annotation>.hasAnnotation() =
    hasAnnotationClass(T::class)

/**
 * Check if this class have an annotation declared by class
 *
 * @since 0.0.7
 */
fun List<Annotation>.hasAnnotationClass(annotationClass: KClass<out Annotation>): Boolean {
    return any { annotation ->
        val annotationNode = DEFAULT_ANNOTATION_FACTORY(annotation.annotationClass)
        annotationClass in annotationNode
    }
}

/**
 * Get an annotation from this class by the type of the annotation
 *
 * @since 0.0.7
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotation() =
    annotations.getAnnotationClass(T::class) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 *
 * @since 0.0.7
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotations(): List<T> =
    annotations.getAnnotationsClass(T::class) as List<T>

/**
 * Get an annotation from this class by the type of the annotation
 *
 * @since 0.0.7
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotationWithFormattedProperty(formatContext: FormatContext, formatStyle: FormatStyle = BracketStyle) =
    annotations.getAnnotationClass(T::class, formatContext, formatStyle) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 *
 * @since 0.0.7
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotationsWithFormattedProperty(formatContext: FormatContext, formatStyle: FormatStyle = BracketStyle): List<T> =
    annotations.getAnnotationsClass(T::class, formatContext, formatStyle) as List<T>

/**
 * Get an annotation from this list of annotations by the type of the annotation
 *
 * @since 0.0.7
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotation() =
    getAnnotationClass(T::class) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 *
 * @since 0.0.7
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotations(): List<T> =
    getAnnotationsClass(T::class) as List<T>

/**
 * Get an annotation from this list of annotations by the type of the annotation
 *
 * @since 0.0.7
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotationWithFormattedProperty(formatContext: FormatContext, formatStyle: FormatStyle = BracketStyle) =
    getAnnotationClass(T::class, formatContext, formatStyle) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 *
 * @since 0.0.7
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotationsWithFormattedProperty(formatContext: FormatContext, formatStyle: FormatStyle = BracketStyle): List<T> =
    getAnnotationsClass(T::class, formatContext, formatStyle) as List<T>

/**
 * Get an annotation from this class by the type of the annotation
 * by class
 *
 * @since 0.0.7
 */
fun List<Annotation>.getAnnotationClass(annotationClass: KClass<out Annotation>): Annotation? {
    val annotation = find { annotationClass in DEFAULT_ANNOTATION_FACTORY(it.annotationClass) } ?: return null
    val annotationNode = DEFAULT_ANNOTATION_FACTORY(annotationClass)
    return DEFAULT_ANNOTATION_ENHANCER(annotation, annotationNode as GenericAnnotationNode)
}

/**
 * Get multiple annotations from this class by the type of the annotation
 * by class
 *
 * @since 0.0.7
 */
fun List<Annotation>.getAnnotationsClass(annotationClass: KClass<out Annotation>): List<Annotation> {
    return filter { annotationClass in DEFAULT_ANNOTATION_FACTORY(it.annotationClass) }
        .map {
            val annotationNode = DEFAULT_ANNOTATION_FACTORY(annotationClass)
            DEFAULT_ANNOTATION_ENHANCER(it, annotationNode as GenericAnnotationNode)
        }
}

/**
 * Get an annotation with property formatted from this class by the type of the annotation
 * by class
 *
 * @since 0.0.7
 */
fun List<Annotation>.getAnnotationClass(annotationClass: KClass<out Annotation>, formatContext: FormatContext, formatStyle: FormatStyle = BracketStyle): Annotation? {
    val annotation = find { annotationClass in DEFAULT_ANNOTATION_FACTORY(it.annotationClass) } ?: return null
    val annotationNode = DEFAULT_ANNOTATION_FACTORY(annotationClass)
    return DEFAULT_ANNOTATION_ENHANCER(annotation, annotationNode as GenericAnnotationNode, formatContext, formatStyle)
}

/**
 * Get multiple annotations with property formatted from this class by the type of the annotation
 * by class
 *
 * @since 0.0.7
 */
fun List<Annotation>.getAnnotationsClass(annotationClass: KClass<out Annotation>, formatContext: FormatContext, formatStyle: FormatStyle): List<Annotation> {
    return filter { annotationClass in DEFAULT_ANNOTATION_FACTORY(it.annotationClass) }
        .map {
            val annotationNode = DEFAULT_ANNOTATION_FACTORY(annotationClass)
            DEFAULT_ANNOTATION_ENHANCER(it, annotationNode as GenericAnnotationNode, formatContext, formatStyle)
        }
}