package kingmc.util.annotation

import kingmc.util.annotation.impl.GenericAnnotationNode
import kingmc.util.format.BracketStyle
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatter

/**
 * A superinterface to instantiate enhanced annotations using
 * cglib or jdk runtime proxy
 *
 * @since 0.0.7
 * @author kingsthere
 */
interface AnnotationEnhancer {
    /**
     * Invoke this mocker to instantiate the annotation
     *
     * @return the mocked annotation instantiated
     */
    operator fun invoke(annotation: Annotation, annotationNode: GenericAnnotationNode): Annotation

    /**
     * Invoke this mocker to instantiate an annotation, the string properties of annotation
     * will be formatted by [formatContext] in [formatter]
     *
     * @return the mocked annotation instantiated
     */
    operator fun invoke(annotation: Annotation, annotationNode: GenericAnnotationNode, formatContext: FormatContext, formatter: Formatter = BracketStyle): Annotation
}