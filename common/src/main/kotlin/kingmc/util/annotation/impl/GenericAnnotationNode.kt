package kingmc.util.annotation.impl

import kingmc.util.annotation.model.AnnotationNode
import kotlin.reflect.KClass

/**
 * Extended [AnnotationNode] exposed the [annotationClass] that this
 * annotation node load from
 *
 * @since 0.1
 * @author kingsthere
 */
abstract class GenericAnnotationNode : AnnotationNode {
    /**
     * The annotation class from this annotation node
     */
    abstract val annotationClass: KClass<out Annotation>
}