package kingmc.util.annotation

/**
 * Thrown when a recursive annotation detected trying to use [ANNOTATION_TREE_FACTORY] or [REFLECTION_ANNOTATION_ACCESSOR]
 */
class RecursiveAnnotationException(message: String?, val annotationName: String) : Exception(message) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RecursiveAnnotationException) return false

        return annotationName == other.annotationName
    }

    override fun hashCode(): Int {
        return annotationName.hashCode()
    }

    override fun toString(): String {
        return "RecursiveAnnotationException(annotation=$annotationName)"
    }
}