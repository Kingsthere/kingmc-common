package kingmc.util.annotation.impl.classgraph

import io.github.classgraph.ClassInfo
import kingmc.util.annotation.model.classgraph.ClassGraphAnnotationAttribute

/**
 * A `ClassGraphAnnotationAttribute` implementation, this returns the raw value from `ClassGraph`
 * instead of loaded values from annotation such as
 *  + Enum
 *  + Nested Annotations
 *  + Classes
 *
 * @since 0.1.2
 * @author kingsthere
 */
data class StaticClassGraphAnnotationAttribute(
    override val annotation: ClassInfo,
    override val name: String,
    private val _value: Any?
) : ClassGraphAnnotationAttribute {
    /**
     * The value of this annotation attribute
     */
    override val value: Any?
        get() = when (_value) {
            else -> _value
        }
}