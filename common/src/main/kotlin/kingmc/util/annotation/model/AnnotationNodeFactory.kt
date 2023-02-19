package kingmc.util.annotation.model

import kotlin.reflect.KClass

/**
 * Factory superinterface for creating [AnnotationNode]
 *
 * @since 0.1
 * @author kingsthere
 */
interface AnnotationNodeFactory {
    /**
     * Create an [AnnotationNode] from [annotationClass]
     */
    operator fun invoke(annotationClass: KClass<out Annotation>): AnnotationNode
}