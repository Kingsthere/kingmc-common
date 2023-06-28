package kingmc.util.annotation.model

import kotlin.reflect.KClass

/**
 * A superinterface to describe an attributes that is defined from annotation class
 *
 * @since 0.1.0
 * @author kingsthere
 */
interface AnnotationAttribute {
    /**
     * The annotation class that this attribute belong to
     */
    val annotation: KClass<out Annotation>

    /**
     * The name of this annotation attribute
     */
    val name: String

    /**
     * The value of this annotation attribute
     */
    val value: Any?
}