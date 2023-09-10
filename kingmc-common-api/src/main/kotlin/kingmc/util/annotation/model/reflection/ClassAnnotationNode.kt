package kingmc.util.annotation.model.reflection

import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationNode
import kotlin.reflect.KClass

/**
 * A data class describe about an annotation from a class
 *
 * @author kingsthere
 * @since 0.1.0
 */
open class ClassAnnotationNode(
    attributes: List<AnnotationAttribute>,
    /**
     * The annotation class of this node
     */
    val annotation: KClass<out Annotation>
) : AnnotationNode(
    attributes
)