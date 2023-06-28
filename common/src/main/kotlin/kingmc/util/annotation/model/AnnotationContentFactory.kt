package kingmc.util.annotation.model

/**
 * Factory superinterface for creating [AnnotationContent]
 *
 * @since 0.0.7
 * @author kingsthere
 */
interface AnnotationContentFactory {
    /**
     * Create an [AnnotationContent] from specific [annotation]
     */
    operator fun invoke(annotation: Annotation): AnnotationContent
}