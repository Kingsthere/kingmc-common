package kingmc.util.annotation.impl.cglib

import kingmc.util.annotation.model.AnnotationContext
import kingmc.util.annotation.model.RawAnnotationAttribute

class CGLIBRawAnnotationAttribute(override val name: String) : RawAnnotationAttribute {
    /**
     * To get the actual value from this attribute
     */
    override fun invoke(context: AnnotationContext): Any? =
        context[name]

    override fun toString(): String {
        return "CGLIBRawAnnotationAttribute(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CGLIBRawAnnotationAttribute

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}