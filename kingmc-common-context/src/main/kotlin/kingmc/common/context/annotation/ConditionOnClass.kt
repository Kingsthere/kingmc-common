package kingmc.common.context.annotation

import io.github.classgraph.AnnotationClassRef
import kingmc.common.context.beans.LoadingBeanDefinition
import kingmc.common.context.source.ClassGraphBeanSource
import kingmc.common.context.source.ClassGraphConditionContext

object ConditionOnClass : Condition {
    /**
     * Test if the given bean is available for the given context
     */
    @Suppress("UNCHECKED_CAST")
    override fun test(bean: LoadingBeanDefinition, context: ConditionContext): Boolean {
        val beanSource = context.getBeanSource()
        // Validate
        require(context is ClassGraphConditionContext)
        require(beanSource is ClassGraphBeanSource) { "ConditionOnClass only supports for ClassGraphBeanSource(s)" }

        val annotation = context.annotationInfo

        val classnames = annotation.getAttributeOrNull("classnames") as Array<Any>? ?: emptyArray()
        val classes = annotation.getAttributeOrNull("classes") as Array<Any>? ?: emptyArray()
        val classLoader = beanSource.classLoader

        val value =
            try {
                for (classname in classnames) {
                    classLoader.loadClass(classname as String)
                }
                for (clazz in classes) {
                    (clazz as AnnotationClassRef).loadClass()
                }
                true
            } catch (_: NoClassDefFoundError) {
                false
            } catch (_: ClassNotFoundException) {
                false
            }
        return value
    }
}