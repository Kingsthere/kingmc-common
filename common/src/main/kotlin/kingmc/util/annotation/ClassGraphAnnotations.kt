package kingmc.util.annotation

import io.github.classgraph.AnnotationInfo
import io.github.classgraph.ClassInfo
import io.github.classgraph.ClassMemberInfo
import kingmc.util.annotation.impl.classgraph.ClassGraphAnnotationAccessorImpl
import kingmc.util.annotation.model.AnnotationContent
import kingmc.util.annotation.model.classgraph.ClassGraphAnnotationAccessor

val CLASS_GRAPH_ANNOTATION_ACCESSOR: ClassGraphAnnotationAccessor = ClassGraphAnnotationAccessorImpl

/**
 * Check if this class is annotated with the given annotation
 */
fun ClassInfo.hasAnnotationClass(targetAnnotation: ClassInfo) =
    this.annotations.hasAnnotationClass(targetAnnotation)

/**
 * Check if this class member is annotated with the given annotation
 */
fun ClassMemberInfo.hasAnnotationClass(targetAnnotation: ClassInfo) =
    this.annotationInfo.hasAnnotationInfoClass(targetAnnotation)

/**
 * Check if this class is annotated with the given annotation
 */
fun ClassInfo.hasAnnotationClassname(targetAnnotationClassname: String) =
    this.annotations.hasAnnotationClassname(targetAnnotationClassname)

/**
 * Check if this class member is annotated with the given annotation
 */
fun ClassMemberInfo.hasAnnotationClassname(targetAnnotationClassname: String) =
    this.annotationInfo.hasAnnotationInfoClassname(targetAnnotationClassname)

/**
 * Check if any of annotations in this collection is or inherited [targetAnnotation]
 */
fun Collection<ClassInfo>.hasAnnotationClass(targetAnnotation: ClassInfo): Boolean {
    return any { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation, targetAnnotation) }
}

/**
 * Check if any of annotations in this collection is or inherited [targetAnnotationClassname]
 */
fun Collection<ClassInfo>.hasAnnotationClassname(targetAnnotationClassname: String): Boolean {
    return any { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation, targetAnnotationClassname) }
}

/**
 * Check if any of annotations in this collection is or inherited [targetAnnotation]
 */
fun Collection<AnnotationInfo>.hasAnnotationInfoClass(targetAnnotation: ClassInfo): Boolean {
    return any { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation.classInfo, targetAnnotation) }
}

/**
 * Check if any of annotations in this collection is or inherited [targetAnnotationClassname]
 */
fun Collection<AnnotationInfo>.hasAnnotationInfoClassname(targetAnnotationClassname: String): Boolean {
    return any { annotation ->
        CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(
            annotation.classInfo,
            targetAnnotationClassname
        )
    }
}

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotation] from this
 * class info
 */
fun ClassInfo.getAnnotationContent(targetAnnotation: ClassInfo) =
    this.annotationInfo.getAnnotationContent(targetAnnotation)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this
 * class info
 */
fun ClassInfo.getAnnotationContent(targetAnnotationClassname: String) =
    this.annotationInfo.getAnnotationContent(targetAnnotationClassname)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotation] from this
 * class info
 */
fun ClassMemberInfo.getAnnotationContent(targetAnnotation: ClassInfo) =
    this.annotationInfo.getAnnotationContent(targetAnnotation)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this
 * class info
 */
fun ClassMemberInfo.getAnnotationContent(targetAnnotationClassname: String) =
    this.annotationInfo.getAnnotationContent(targetAnnotationClassname)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotation] from this
 * class info
 */
fun ClassInfo.getAnnotationContentStatic(targetAnnotation: ClassInfo) =
    this.annotationInfo.getAnnotationContentStatic(targetAnnotation)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this
 * class info
 */
fun ClassInfo.getAnnotationContentStatic(targetAnnotationClassname: String) =
    this.annotationInfo.getAnnotationContentStatic(targetAnnotationClassname)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotation] from this
 * class info
 */
fun ClassMemberInfo.getAnnotationContentStatic(targetAnnotation: ClassInfo) =
    this.annotationInfo.getAnnotationContentStatic(targetAnnotation)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this
 * class info
 */
fun ClassMemberInfo.getAnnotationContentStatic(targetAnnotationClassname: String) =
    this.annotationInfo.getAnnotationContentStatic(targetAnnotationClassname)

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotation] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContent(targetAnnotation: ClassInfo): AnnotationContent? {
    val annotationFound =
        find { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation.classInfo, targetAnnotation) }
            ?: return null
    return CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentFor(annotationFound)
}

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContent(targetAnnotationClassname: String): AnnotationContent? {
    val annotationFound = find { annotation ->
        CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(
            annotation.classInfo,
            targetAnnotationClassname
        )
    } ?: return null
    return CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentFor(annotationFound)
}

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotation] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContentStatic(targetAnnotation: ClassInfo): AnnotationContent? {
    val annotationFound =
        find { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation.classInfo, targetAnnotation) }
            ?: return null
    return CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentStaticFor(annotationFound)
}

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContentStatic(targetAnnotationClassname: String): AnnotationContent? {
    val annotationFound = find { annotation ->
        CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(
            annotation.classInfo,
            targetAnnotationClassname
        )
    } ?: return null
    return CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentStaticFor(annotationFound)
}

/**
 * Gets all annotation content for annotations that matches the [targetAnnotation] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContents(targetAnnotation: ClassInfo): List<AnnotationContent> {
    val annotationFound =
        filter { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation.classInfo, targetAnnotation) }
    return annotationFound.map { CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentFor(it) }
}

/**
 * Gets all annotation content for annotations that matches the [targetAnnotationClassname] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContents(targetAnnotationClassname: String): List<AnnotationContent> {
    val annotationFound = filter { annotation ->
        CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(
            annotation.classInfo,
            targetAnnotationClassname
        )
    }
    return annotationFound.map { CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentFor(it) }
}

/**
 * Gets the annotation contents for the annotation that matches the [targetAnnotation] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContentsStatic(targetAnnotation: ClassInfo): List<AnnotationContent> {
    val annotationFound =
        filter { annotation -> CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(annotation.classInfo, targetAnnotation) }
    return annotationFound.map { CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentStaticFor(it) }
}

/**
 * Gets the annotation content for the annotation that matches the [targetAnnotationClassname] from this collection
 * of annotation info
 */
fun Collection<AnnotationInfo>.getAnnotationContentsStatic(targetAnnotationClassname: String): List<AnnotationContent> {
    val annotationFound = filter { annotation ->
        CLASS_GRAPH_ANNOTATION_ACCESSOR.hasAnnotation(
            annotation.classInfo,
            targetAnnotationClassname
        )
    }
    return annotationFound.map { CLASS_GRAPH_ANNOTATION_ACCESSOR.createContentStaticFor(it) }
}