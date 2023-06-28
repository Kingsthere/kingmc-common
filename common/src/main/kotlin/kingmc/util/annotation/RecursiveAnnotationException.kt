package kingmc.util.annotation

import kotlin.reflect.KClass

/**
 * Thrown when a recursive annotation detected trying to use [ANNOTATION_TREE_FACTORY] or [ANNOTATION_CONTENT_FACTORY]
 */
class RecursiveAnnotationException(message: String?, val annotation: KClass<out Annotation>) : Exception(message) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RecursiveAnnotationException) return false

        return annotation == other.annotation
    }

    override fun hashCode(): Int {
        return annotation.hashCode()
    }

    override fun toString(): String {
        return "RecursiveAnnotationException(annotation=$annotation)"
    }
}