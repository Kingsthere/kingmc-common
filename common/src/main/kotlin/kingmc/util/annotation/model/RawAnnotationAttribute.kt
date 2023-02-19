package kingmc.util.annotation.model

/**
 * An interface represent a raw annotation attribute defined in
 * an annotation, it contains
 *  + The [name] of the attribute
 *  + The method to get this attribute from an [AnnotationContext]
 *
 * @since 0.1
 * @author kingsthere
 */
interface RawAnnotationAttribute {
    /**
     * The [name] of this annotation attribute
     */
    val name: String

    /**
     * To get the actual value from this attribute
     *
     * @param context the annotation context to get from
     */
    operator fun invoke(context: AnnotationContext): Any?
}