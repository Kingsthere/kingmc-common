package kingmc.util.annotation.model

/**
 * A generic implementation of [AnnotationContext] uses [HashMap]
 */
class GenericAnnotationContext(val value: Map<String, Any>) : AnnotationContext, Map<String, Any> by value {
    override fun equals(other: Any?): Boolean {
        return value == other
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}