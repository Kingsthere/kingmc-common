package kingmc.util.annotation

import kingmc.util.annotation.cglib.CGLIBAnnotationEnhancer
import kingmc.util.annotation.impl.reflection.ReflectionAnnotationAccessorImpl
import kingmc.util.annotation.model.reflection.ReflectionAnnotationAccessor
import kingmc.util.format.BracketStyle
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatter
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

val REFLECTION_ANNOTATION_ACCESSOR: ReflectionAnnotationAccessor = ReflectionAnnotationAccessorImpl
val ANNOTATION_ENHANCER: AnnotationEnhancer = CGLIBAnnotationEnhancer

/**
 * Check if this class is annotated with the given annotation [T]
 */
inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation() =
    annotations.hasAnnotationClass(T::class)

/**
 * Check if this class is annotated with the given annotation [T]
 */
inline fun <reified T : Annotation> List<Annotation>.hasAnnotation() =
    hasAnnotationClass(T::class)

/**
 * Check if any of annotations in this collection is or inherited [targetAnnotation]
 */
fun Collection<Annotation>.hasAnnotationClass(targetAnnotation: KClass<out Annotation>): Boolean {
    return any { annotation -> REFLECTION_ANNOTATION_ACCESSOR.hasAnnotation(annotation.annotationClass, targetAnnotation) }
}

/**
 * Get an annotation from this class by the type of the annotation
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotation() =
    annotations.getAnnotationByClass(T::class) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotations(): List<T> =
    annotations.getAnnotationsByClass(T::class) as List<T>

/**
 * Get an annotation from this class by the type of the annotation
 */
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotationWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle) =
    annotations.getAnnotationByClass(T::class, formatContext, formatter) as? T

/**
 * Get multiple annotations from this class by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> KAnnotatedElement.getAnnotationsWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle): List<T> =
    annotations.getAnnotationsByClass(T::class, formatContext, formatter) as List<T>

/**
 * Get an annotation from this list of annotations by the type of the annotation
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotation() =
    getAnnotationByClass(T::class) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotations(): List<T> =
    getAnnotationsByClass(T::class) as List<T>

/**
 * Get an annotation from this list of annotations by the type of the annotation
 */
inline fun <reified T : Annotation> List<Annotation>.getAnnotationWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle) =
    getAnnotationByClass(T::class, formatContext, formatter) as? T

/**
 * Get multiple annotations from this list of annotations by the type of the annotation
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Annotation> List<Annotation>.getAnnotationsWithFormattedProperty(formatContext: FormatContext, formatter: Formatter = BracketStyle): List<T> =
    getAnnotationsByClass(T::class, formatContext, formatter) as List<T>

/**
 * Instantiate an enhanced `Annotation` from this list of annotations that is or inherited [targetAnnotation]
 */
fun Collection<Annotation>.getAnnotationByClass(targetAnnotation: KClass<out Annotation>): Annotation? {
    val annotationFound = find { annotation -> REFLECTION_ANNOTATION_ACCESSOR.hasAnnotation(annotation.annotationClass, targetAnnotation) } ?: return null
    return ANNOTATION_ENHANCER(annotationFound, targetAnnotation)
}

/**
 * Instantiate a list of `Annotation`s from this list of annotations that is or inherited [targetAnnotation]
 */
fun List<Annotation>.getAnnotationsByClass(targetAnnotation: KClass<out Annotation>): List<Annotation> {
    val annotations = filter { annotation -> REFLECTION_ANNOTATION_ACCESSOR.hasAnnotation(annotation.annotationClass, targetAnnotation) }
    return annotations.map { annotationFound -> ANNOTATION_ENHANCER(annotationFound, targetAnnotation) }
}

/**
 * Instantiate an enhanced `Annotation` from this list of annotations that is or inherited [targetAnnotation]
 */
fun Collection<Annotation>.getAnnotationByClass(targetAnnotation: KClass<out Annotation>, formatContext: FormatContext, formatter: Formatter = BracketStyle): Annotation? {
    val annotationFound = find { annotation -> REFLECTION_ANNOTATION_ACCESSOR.hasAnnotation(annotation.annotationClass, targetAnnotation) } ?: return null
    return ANNOTATION_ENHANCER(annotationFound, targetAnnotation, formatContext, formatter)
}

/**
 * Instantiate a list of `Annotation`s from this list of annotations that is or inherited [targetAnnotation]
 */
fun List<Annotation>.getAnnotationsByClass(targetAnnotation: KClass<out Annotation>, formatContext: FormatContext, formatter: Formatter = BracketStyle): List<Annotation> {
    val annotations = filter { annotation -> REFLECTION_ANNOTATION_ACCESSOR.hasAnnotation(annotation.annotationClass, targetAnnotation) }
    return annotations.map { annotationFound -> ANNOTATION_ENHANCER(annotationFound, targetAnnotation, formatContext, formatter) }
}
