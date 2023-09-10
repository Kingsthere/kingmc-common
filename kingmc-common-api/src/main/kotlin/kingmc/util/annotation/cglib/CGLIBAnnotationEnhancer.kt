package kingmc.util.annotation.cglib

import kingmc.util.annotation.AnnotationEnhancer
import kingmc.util.annotation.impl.IGNORED_ATTRIBUTES
import kingmc.util.annotation.impl.reflection.ReflectionAnnotationAccessorImpl
import kingmc.util.annotation.model.reflection.ReflectionAnnotationAccessor
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatter
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import kotlin.reflect.KClass

/**
 * CGLIB [AnnotationEnhancer] implementation
 *
 * @author kingsthere
 * @since 0.1.0
 */
object CGLIBAnnotationEnhancer : AnnotationEnhancer {
    val annotationContentAccessor: ReflectionAnnotationAccessor = ReflectionAnnotationAccessorImpl

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
        val annotationContent = annotationContentAccessor.createContentFor(annotation)
        enhancer.setSuperclass(templateClass.java)
        enhancer.setCallback(MethodInterceptor { obj, method, args, proxy ->
            if (method.name in IGNORED_ATTRIBUTES) {
                return@MethodInterceptor proxy.invokeSuper(obj, args)
            }
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
        val annotationContent = annotationContentAccessor.createContentFor(annotation)
        enhancer.setSuperclass(templateClass.java)
        enhancer.setCallback(MethodInterceptor { obj, method, args, proxy ->
            if (method.name in IGNORED_ATTRIBUTES) {
                return@MethodInterceptor proxy.invokeSuper(obj, args)
            }
            return@MethodInterceptor annotationContent.getAttribute(method.name)
        })
        return enhancer.create() as TTemplate
    }
}