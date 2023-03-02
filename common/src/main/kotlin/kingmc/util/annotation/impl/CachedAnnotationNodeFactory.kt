package kingmc.util.annotation.impl

import kingmc.util.annotation.model.AnnotationNode
import kingmc.util.annotation.model.AnnotationNodeFactory
import kotlin.reflect.KClass

/**
 * Abstract implementation of [AnnotationNodeFactory] cached the [AnnotationNode]
 * that created in this factory
 *
 * @since 0.0.7
 * @author kingsthere
 * @see AnnotationNodeFactory
 */
abstract class CachedAnnotationNodeFactory : AnnotationNodeFactory {
    protected val cached: MutableMap<KClass<out Annotation>, AnnotationNode> = mutableMapOf()

    override fun invoke(annotationClass: KClass<out Annotation>): AnnotationNode {
        return if (annotationClass !in this.cached) {
            val cachedAnnotationNode = provideCached(annotationClass)
            this.cached[annotationClass] = cachedAnnotationNode
            cachedAnnotationNode
        } else {
            this.cached[annotationClass]!!
        }
    }

    /**
     * Implement this method instead of [invoke] if you wish
     * to cache the annotation created
     */
    abstract fun provideCached(annotationClass: KClass<out Annotation>): AnnotationNode
}