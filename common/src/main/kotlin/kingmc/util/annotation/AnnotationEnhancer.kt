package kingmc.util.annotation

import kingmc.util.format.BracketStyle
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatter
import kotlin.reflect.KClass

/**
 * A superinterface to instantiate enhanced annotations using cglib or jdk runtime proxy
 *
 * @since 0.1.0
 * @author kingsthere
 */
interface AnnotationEnhancer {
    /**
     * Invoke this mocker to instantiate an `Annotation` by [templateClass], the string properties of annotation
     * will be formatted by [formatContext] in [formatter]
     *
     * @return the mocked annotation instantiated
     */
    operator fun <TTemplate : Any> invoke(annotation: Annotation, templateClass: KClass<out TTemplate>, formatContext: FormatContext, formatter: Formatter = BracketStyle): TTemplate

    /**
     * Invoke this mocker to instantiate an `Annotation` by [templateClass]
     *
     * @return the mocked annotation instantiated
     */
    operator fun <TTemplate : Any> invoke(annotation: Annotation, templateClass: KClass<out TTemplate>): TTemplate
}