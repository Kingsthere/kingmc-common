package kingmc.util.annotation.model.classgraph

import io.github.classgraph.ClassInfo
import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationNode
import kotlin.reflect.KClass

/**
 * A data class describe about an annotation from a [ClassInfo]
 *
 * @author kingsthere
 * @since 0.1.0
 */
open class ClassGraphAnnotationNode(
    attributes: List<AnnotationAttribute>,
    /**
     * The annotation class info of this node
     */
    val annotation: ClassInfo
) : AnnotationNode(
    attributes
)