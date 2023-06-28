package kingmc.util.annotation

import kingmc.util.annotation.impl.AnnotationContentFactoryImpl
import kingmc.util.annotation.impl.AnnotationTreeFactoryImpl
import kingmc.util.annotation.impl.cglib.CGLIBAnnotationEnhancer
import kingmc.util.annotation.model.AnnotationContentFactory
import kingmc.util.annotation.model.AnnotationTreeFactory
import kingmc.util.format.BracketStyle
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatter
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

val ANNOTATION_CONTENT_FACTORY: AnnotationContentFactory = AnnotationContentFactoryImpl
val ANNOTATION_TREE_FACTORY: AnnotationTreeFactory = AnnotationTreeFactoryImpl
val ANNOTATION_ENHANCER: AnnotationEnhancer = CGLIBAnnotationEnhancer

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
 * Check if any of annotations in this list is or inherited [targetAnnotation]
 */
fun Collection<Annotation>.hasAnnotationClass(targetAnnotation: KClass<out Annotation>): Boolean {
    return any { annotation -> ANNOTATION_TREE_FACTORY.hasAnnotation(annotation.annotationClass, targetAnnotation) }
}

/**
 * Get an annotation from this class by the type of the annotation
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotation() =
    annotations.getAnnotationClass(T::class) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotations(): List<T> =
    annotations.getAnnotationsClass(T::class) as List<T>

/**
 * Get an annotation from this class by the type of the annotation
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotationWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle) =
    annotations.getAnnotationClass(T::class, formatContext, formatter) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotationsWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle): List<T> =
    annotations.getAnnotationsClass(T::class, formatContext, formatter) as List<T>

/**
 * Get an annotation from this list of annotations by the type of the annotation
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotation() =
    getAnnotationClass(T::class) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotations(): List<T> =
    getAnnotationsClass(T::class) as List<T>

/**
 * Get an annotation from this list of annotations by the type of the annotation
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotationWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle) =
    getAnnotationClass(T::class, formatContext, formatter) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotationsWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle): List<T> =
    getAnnotationsClass(T::class, formatContext, formatter) as List<T>

/**
 * Instantiate an enhanced `Annotation` from this list of annotations that is or inherited [targetAnnotation]
 */
fun Collection<Annotation>.getAnnotationClass(targetAnnotation: KClass<out Annotation>): Annotation? {
    val annotationFound = find { annotation -> ANNOTATION_TREE_FACTORY.hasAnnotation(annotation.annotationClass, targetAnnotation) } ?: return null
    return ANNOTATION_ENHANCER(annotationFound, targetAnnotation)
}

/**
 * Instantiate a list of `Annotation`s from this list of annotations that is or inherited [targetAnnotation]
 */
fun List<Annotation>.getAnnotationsClass(targetAnnotation: KClass<out Annotation>): List<Annotation> {
    val annotations = filter { annotation -> ANNOTATION_TREE_FACTORY.hasAnnotation(annotation.annotationClass, targetAnnotation) }
    return annotations.map { annotationFound -> ANNOTATION_ENHANCER(annotationFound, targetAnnotation) }
}

/**
 * Instantiate an enhanced `Annotation` from this list of annotations that is or inherited [targetAnnotation]
 */
fun Collection<Annotation>.getAnnotationClass(targetAnnotation: KClass<out Annotation>, formatContext: FormatContext, formatter: Formatter = BracketStyle): Annotation? {
    val annotationFound = find { annotation -> ANNOTATION_TREE_FACTORY.hasAnnotation(annotation.annotationClass, targetAnnotation) } ?: return null
    return ANNOTATION_ENHANCER(annotationFound, targetAnnotation, formatContext, formatter)
}

/**
 * Instantiate a list of `Annotation`s from this list of annotations that is or inherited [targetAnnotation]
 */
fun List<Annotation>.getAnnotationsClass(targetAnnotation: KClass<out Annotation>, formatContext: FormatContext, formatter: Formatter = BracketStyle): List<Annotation> {
    val annotations = filter { annotation -> ANNOTATION_TREE_FACTORY.hasAnnotation(annotation.annotationClass, targetAnnotation) }
    return annotations.map { annotationFound -> ANNOTATION_ENHANCER(annotationFound, targetAnnotation, formatContext, formatter) }
}
