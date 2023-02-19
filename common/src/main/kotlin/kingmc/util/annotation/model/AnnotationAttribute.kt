package kingmc.util.annotation.model

/**
 * A superinterface to describe how many attributes that is defined
 * in a [AnnotationNode] actually, if more [RawAnnotationAttribute] is declared
 * (in annotation) with aliases than a [AnnotationAttribute] describe more than
 * one raw annotation attributes
 *
 * @since 0.1
 * @author kingsthere
 * @see AnnotationNode
 */
interface AnnotationAttribute {
    /**
     * The [RawAnnotationAttribute] this attribute describing
     */
    val values: List<RawAnnotationAttribute>

    /**
     * To get the actual value from this attribute
     */
    operator fun invoke(context: AnnotationContext): Any?
}