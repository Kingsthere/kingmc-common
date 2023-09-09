package kingmc.util.annotation.model.classgraph

import io.github.classgraph.ClassInfo
import kingmc.util.annotation.model.AnnotationAttribute


/**
 * A superinterface to describe an attributes that is defined from a [ClassInfo] provided
 * by ClassGraph
 *
 * @author kingsthere
 * @since 0.1.0
 */
interface ClassGraphAnnotationAttribute : AnnotationAttribute {
    /**
     * The annotation class info that this attribute belongs to
     */
    val annotation: ClassInfo
}