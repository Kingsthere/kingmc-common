package kingmc.util.annotation.model.reflection

import kingmc.util.annotation.model.AnnotationAttribute
import kotlin.reflect.KClass

/**
 * A superinterface to describe attributes that are defined from an annotation class
 *
 * @author kingsthere
 * @since 0.1.0
 */
interface ClassAnnotationAttribute : AnnotationAttribute {
    /**
     * The annotation class that this attribute belong to
     */
    val annotation: KClass<out Annotation>
}