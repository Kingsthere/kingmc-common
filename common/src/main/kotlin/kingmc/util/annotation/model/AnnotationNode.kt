package kingmc.util.annotation.model

import kotlin.reflect.KClass

/**
 * A data class describe about an annotation node listed in [AnnotationContent]
 *
 * @since 0.1.0
 * @author kingsthere
 */
data class AnnotationNode(
    /**
     * The annotation class of this node
     */
    val annotation: KClass<out Annotation>,

    /**
     * The attributes of this node
     */
    val attributes: List<AnnotationAttribute>
)