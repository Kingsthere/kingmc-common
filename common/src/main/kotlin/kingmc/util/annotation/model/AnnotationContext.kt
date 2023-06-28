package kingmc.util.annotation.model

/**
 * A data class describe the full content of an annotation (include attributes inherited)
 *
 * @property nodes single annotation nodes in this annotation content
 * @constructor create a new annotation content with specific nodes
 * @since 0.1.0
 * @author kingsthere
 */
data class AnnotationContent(
    val node: AnnotationNode
) {
    /**
     * Get an attribute for this annotation content
     *
     * @param name the name of the annotation attribute
     * @return attribute value got
     */
    fun getAttribute(name: String): Any {
        node.attributes.find { attribute -> attribute.name == name }?.let { attribute ->
            return attribute.value!!
        }
        throw IllegalStateException("No attribute named $name found")
    }
}