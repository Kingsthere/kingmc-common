package kingmc.util.annotation

/**
 * A superinterface to instantiate annotations using
 * cglib or jdk runtime proxy
 *
 * @since 0.1
 * @author kingsthere
 * @param TAnnotation the kind of annotation mocking
 */
sealed interface AnnotationMocker<TAnnotation : Annotation> {
    /**
     * Invoke this mocker to instantiate the annotation
     *
     * @return the annotation instantiated
     */
    operator fun invoke(): TAnnotation
}