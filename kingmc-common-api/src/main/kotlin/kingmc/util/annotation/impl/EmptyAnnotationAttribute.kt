package kingmc.util.annotation.impl

import kingmc.util.annotation.model.AnnotationAttribute

data class EmptyAnnotationAttribute(
    override val name: String,
) : AnnotationAttribute {
    override val value: Any?
        get() = null
}