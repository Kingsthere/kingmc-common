package kingmc.util.annotation.model

/**
 * A superinterface to describe attributes that are defined from an annotation
 *
 * @author kingsthere
 * @since 0.1.0
 */
interface AnnotationAttribute {
    /**
     * The name of this annotation attribute
     */
    val name: String

    /**
     * The value of this annotation attribute
     */
    val value: Any?
}