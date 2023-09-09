package kingmc.util.annotation.model

/**
 * A data class describe about an annotation node listed in [AnnotationContent]
 *
 * @author kingsthere
 * @since 0.1.0
 */
open class AnnotationNode(
    /**
     * The attributes of this node
     */
    val attributes: List<AnnotationAttribute>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnnotationNode

        return attributes == other.attributes
    }

    override fun hashCode(): Int {
        return attributes.hashCode()
    }

    override fun toString(): String {
        return "AnnotationNode(attributes=$attributes)"
    }
}