package kingmc.util.annotation.impl.cglib

import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationContext
import kingmc.util.annotation.model.RawAnnotationAttribute

class CGLIBAnnotationAttribute(
    override val values: List<RawAnnotationAttribute>
    ) : AnnotationAttribute {
    /**
     * To get the actual value from this attribute
     */
    override fun invoke(context: AnnotationContext): Any? {
        fun findAdaptedRawAnnotationAttribute(): RawAnnotationAttribute? =
            values.find {
                it(context) != null
            }

        val rawAnnotationAttribute = findAdaptedRawAnnotationAttribute()
            ?: return null
        return rawAnnotationAttribute(context)
    }

    override fun toString(): String {
        return "CGLIBAnnotationAttribute(values=$values)"
    }
}