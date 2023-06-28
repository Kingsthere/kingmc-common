package kingmc.util.annotation.impl

import kingmc.util.annotation.model.AnnotationAttribute
import kotlin.reflect.KClass

data class EmptyAnnotationAttribute(
    override val annotation: KClass<out Annotation>,
    override val name: String,
) : AnnotationAttribute {
    override val value: Any?
        get() = null
}