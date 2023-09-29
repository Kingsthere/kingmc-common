package kingmc.util.annotation.model

/**
 * A data class describes the full content of an annotation (include attributes inherited)
 *
 * @constructor create a new annotation content with specific nodes
 * @author kingsthere
 * @since 0.1.0
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
        throw IllegalArgumentException("No attribute named $name found")
    }

    /**
     * Get an attribute for this annotation content or `null`
     *
     * @param name the name of the annotation attribute
     * @return attribute value got
     */
    fun getAttributeOrNull(name: String): Any? {
        return node.attributes.find { attribute -> attribute.name == name }?.value
    }

    /**
     * Get an attribute for this annotation content or return the given value
     *
     * @param name the name of the annotation attribute
     * @return attribute value got
     */
    fun getAttributeOrElse(name: String, def: Any): Any {
        return getAttributeOrNull(name) ?: def
    }
}