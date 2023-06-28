package kingmc.util.annotation.impl.cglib

import kingmc.util.annotation.AnnotationEnhancer
import kingmc.util.annotation.impl.AnnotationContentFactoryImpl
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatter
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import kotlin.reflect.KClass

/**
 * CGLIB [AnnotationEnhancer] implementation
 *
 * @since 0.1.0
 * @author kingsthere
 */
object CGLIBAnnotationEnhancer : AnnotationEnhancer {
    val annotationContentFactory = AnnotationContentFactoryImpl

    /**
     * Invoke this mocker to instantiate an `Annotation` by [templateClass], the string properties of annotation
     * will be formatted by [formatContext] in [formatter]
     *
     * @return the mocked annotation instantiated
     */
    @Suppress("UNCHECKED_CAST")
    override fun <TTemplate : Any> invoke(
        annotation: Annotation,
        templateClass: KClass<out TTemplate>,
        formatContext: FormatContext,
        formatter: Formatter
    ): TTemplate {
        val enhancer = Enhancer()
        val annotationContent = annotationContentFactory(annotation)
        enhancer.setSuperclass(templateClass.java)
        enhancer.setCallback(MethodInterceptor { _, method, _, _ ->
            val value = annotationContent.getAttribute(method.name)
            if (value is String) {
                return@MethodInterceptor formatter.format(value, formatContext)
            } else {
                return@MethodInterceptor value
            }
        })
        return enhancer.create() as TTemplate
    }

    /**
     * Invoke this mocker to instantiate an `Annotation` by [templateClass]
     *
     * @return the mocked annotation instantiated
     */
    @Suppress("UNCHECKED_CAST")
    override fun <TTemplate : Any> invoke(annotation: Annotation, templateClass: KClass<out TTemplate>): TTemplate {
        val enhancer = Enhancer()
        val annotationContent = annotationContentFactory(annotation)
        enhancer.setSuperclass(templateClass.java)
        enhancer.setCallback(MethodInterceptor { _, method, _, _ ->
            return@MethodInterceptor annotationContent.getAttribute(method.name)
        })
        return enhancer.create() as TTemplate
    }
}