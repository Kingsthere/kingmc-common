package kingmc.util.annotation.model

/**
 * A superinterface to describe a node on annotation, it uses
 * tree structure to describe the model of an annotation
 *
 * @since 0.1
 * @author kingsthere
 */
interface AnnotationNode {
    /**
     * [AnnotationNode] that this node is inherited from
     */
    val inherited: List<AnnotationNode>

    /**
     * The attributes defined in this annotation
     */
    val attributes: List<AnnotationAttribute>

    /**
     * The attributes defined in this annotation and [inherited]
     * annotations
     */
    val declaredAttributes: List<AnnotationAttribute>
}