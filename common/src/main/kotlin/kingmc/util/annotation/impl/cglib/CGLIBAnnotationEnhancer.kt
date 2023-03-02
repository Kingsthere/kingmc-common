package kingmc.util.annotation.impl.cglib

import kingmc.util.annotation.AnnotationEnhancer
import kingmc.util.annotation.impl.GenericAnnotationNode
import kingmc.util.annotation.model.GenericAnnotationContext
import kingmc.util.format.FormatContext
import kingmc.util.format.FormatStyle
import kingmc.util.format.formatWithStyle
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import java.lang.reflect.Method
import kotlin.reflect.full.declaredMemberProperties

/**
 * Mocker to instantiate enhanced annotations using cglib
 *
 * @since 0.0.7
 * @author kingsthere
 */
object CGLIBAnnotationEnhancer : AnnotationEnhancer {
    override operator fun invoke(annotation: Annotation, annotationNode: GenericAnnotationNode): Annotation {
        val enhancer = Enhancer()
        val context = GenericAnnotationContext(buildMap {
            annotation.annotationClass.declaredMemberProperties.forEach {
                val value = it.call(annotation)!!
                if (value is String) {
                    if (value.isNotEmpty()) {
                        put(it.name, value)
                    }
                } else if (value is Number) {
                    if (value != -1) {
                        put(it.name, value)
                    }
                } else {
                    put(it.name, value)
                }
            }
        })
        enhancer.namingPolicy = KtilAnnotationNamingPolicy
        enhancer.setInterfaces(arrayOf(annotationNode.annotationClass.java))
        enhancer.setCallback(object : MethodInterceptor {
            override fun intercept(obj: Any, method: Method, args: Array<out Any>, proxy: MethodProxy): Any {
                val attribute = annotationNode.attributes
                    .find { attribute ->
                        attribute.values.any { it.name == method.name }
                    }

                return attribute?.invoke(context)
                    ?: method.defaultValue ?: throw IllegalStateException("Unable to find annotation attribute ${method.name}")
            }
        })
        return enhancer.create() as Annotation
    }

    /**
     * Invoke this mocker to instantiate an annotation, the string properties of annotation
     * will be formatted by [formatContext] in [formatStyle]
     *
     * @return the mocked annotation instantiated
     */
    override fun invoke(
        annotation: Annotation,
        annotationNode: GenericAnnotationNode,
        formatContext: FormatContext,
        formatStyle: FormatStyle
    ): Annotation {
        val enhancer = Enhancer()
        val context = GenericAnnotationContext(buildMap {
            annotation.annotationClass.declaredMemberProperties.forEach {
                val value = it.call(annotation)!!
                put(it.name, value)
            }
        })
        enhancer.namingPolicy = KtilAnnotationNamingPolicy
        enhancer.setInterfaces(arrayOf(annotationNode.annotationClass.java))
        enhancer.setCallback(object : MethodInterceptor {
            override fun intercept(obj: Any, method: Method, args: Array<out Any>, proxy: MethodProxy): Any {
                val attribute = annotationNode.attributes
                    .find { attribute ->
                        attribute.values.any { it.name == method.name }
                    }
                val value = attribute?.invoke(context)
                    ?: method.defaultValue ?: throw IllegalStateException("Unable to find annotation attribute ${method.name}")
                return if (value is String) {
                    // Format string
                    value.formatWithStyle(formatStyle, formatContext)
                } else {
                    value
                }
            }
        })
        return enhancer.create() as Annotation
    }
}