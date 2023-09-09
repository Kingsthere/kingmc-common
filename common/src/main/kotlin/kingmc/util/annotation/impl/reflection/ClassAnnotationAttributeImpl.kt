package kingmc.util.annotation.impl.reflection

import kingmc.util.annotation.model.reflection.ClassAnnotationAttribute
import kotlin.reflect.KClass

data class ClassAnnotationAttributeImpl(
    override val annotation: KClass<out Annotation>,
    override val name: String,
    override val value: Any?
) : ClassAnnotationAttribute