package kingmc.common.context.annotation

import io.github.classgraph.AnnotationClassRef
import kingmc.common.context.beans.LoadingBeanDefinition
import kingmc.common.context.source.ClassGraphBeanSource
import kingmc.common.context.source.ClassGraphConditionContext

object ConditionOnClassMissing : Condition {
    /**
     * Test if the given bean is available for the given context
     */
    @Suppress("UNCHECKED_CAST")
    override fun test(bean: LoadingBeanDefinition, context: ConditionContext): Boolean {
        val beanSource = context.getBeanSource()
        // Validate
        require(context is ClassGraphConditionContext)
        require(beanSource is ClassGraphBeanSource) { "ConditionOnClassMissing only supports for ClassGraphBeanSource(s)" }

        val annotation = context.annotationInfo

        val classnames = annotation.getAttributeOrNull("classnames") as Array<Any>? ?: emptyArray()
        val classes = annotation.getAttributeOrNull("classes") as Array<Any>? ?: emptyArray()
        val classLoader = beanSource.classLoader

        return classnames.all {
            try {
                classLoader.loadClass(it as String)
                false
            } catch (_: ClassNotFoundException) {
                true
            }
        } && classes.all {
            try {
                classLoader.loadClass((it as AnnotationClassRef).name)
                false
            } catch (_: ClassNotFoundException) {
                true
            }
        }
    }
}