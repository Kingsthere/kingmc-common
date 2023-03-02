package kingmc.util.annotation.impl.cglib

import kingmc.util.annotation.impl.CachedAnnotationNodeFactory
import kingmc.util.annotation.model.AnnotationNode
import kingmc.util.annotation.model.AnnotationNodeFactory
import kotlin.reflect.KClass

/**
 * A abstract implementation of [AnnotationNodeFactory]
 *
 * @since 0.0.7
 * @author kingsthere
 */
object CGLIBAnnotationNodeFactory : CachedAnnotationNodeFactory() {
    /**
     * Implement this method instead of [invoke] if you wish
     * to cache the annotation created
     */
    override fun provideCached(annotationClass: KClass<out Annotation>): AnnotationNode {
        return CGLIBAnnotationNode(annotationClass)
    }

}