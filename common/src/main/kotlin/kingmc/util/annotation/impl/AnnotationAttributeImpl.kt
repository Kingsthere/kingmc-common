package kingmc.util.annotation.impl

import kingmc.util.annotation.model.AnnotationAttribute
import kotlin.reflect.KClass

data class AnnotationAttributeImpl(
    override val annotation: KClass<out Annotation>,
    override val name: String,
    override val value: Any?
) : AnnotationAttribute