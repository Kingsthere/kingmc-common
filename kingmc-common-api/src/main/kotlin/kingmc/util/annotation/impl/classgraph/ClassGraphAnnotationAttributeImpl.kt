package kingmc.util.annotation.impl.classgraph

import io.github.classgraph.AnnotationClassRef
import io.github.classgraph.AnnotationEnumValue
import io.github.classgraph.AnnotationInfo
import io.github.classgraph.ClassInfo
import kingmc.util.annotation.model.classgraph.ClassGraphAnnotationAttribute

/**
 * A `ClassGraphAnnotationAttribute` implementation returns the loaded value from [ClassInfo] provided
 * by `ClassGraph`, this cause annotation classes to load. If you wish to read annotation attribute
 * without loading classes, considered to use [StaticClassGraphAnnotationAttribute] instead
 *
 * @since 0.1.2
 * @author kingsthere
 */
data class ClassGraphAnnotationAttributeImpl(
    override val annotation: ClassInfo,
    override val name: String,
    private val _value: Any?
) : ClassGraphAnnotationAttribute {
    /**
     * The value of this annotation attribute
     */
    override val value: Any?
        get() = when (_value) {
            is AnnotationEnumValue -> _value.loadClassAndReturnEnumValue()
            is AnnotationClassRef -> _value.loadClass()
            is AnnotationInfo -> _value.loadClassAndInstantiate()
            else -> _value
        }
}