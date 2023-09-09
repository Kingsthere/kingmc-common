package kingmc.common.context.annotation

import io.github.classgraph.AnnotationClassRef
import kingmc.common.context.beans.LoadingBeanDefinition
import kingmc.common.context.source.ClassGraphBeanSource
import kingmc.common.context.source.ClassGraphConditionContext

object ConditionOnBeanMissing : Condition {
    private fun checkBeanName(bean: LoadingBeanDefinition, context: ConditionContext, beanSource: ClassGraphBeanSource, beanName: String): Boolean {
        // Try to get the loading bean definition instance from beanSource
        // Return false if the bean with that name does not exist
        val loadingBeanDefinition = beanSource.getLoadingBeanDefinition(beanName) ?: return false

        // Check if loading bean definition is the testing bean
        if (loadingBeanDefinition == bean) {
            throw IllegalArgumentException("Recursive ConditionOnBean check")
        }

        // Check the condition for given bean
        if (!context.isBeanAvailable(loadingBeanDefinition)) {
            return false
        }

        // Abstract bean logic
        if (loadingBeanDefinition.isAbstract()) {
            // Check implementation for abstract bean
            if (loadingBeanDefinition.implementations().isEmpty()) {
                return false
            }
        }
        return true
    }

    /**
     * Test if the given bean is not available for the given context
     */
    @Suppress("UNCHECKED_CAST")
    override fun test(bean: LoadingBeanDefinition, context: ConditionContext): Boolean {
        val beanSource = context.getBeanSource()
        // Validate
        require(context is ClassGraphConditionContext)
        require(beanSource is ClassGraphBeanSource) { "ConditionOnBeanMissing only supports for ClassGraphBeanSource(s)" }

        val annotation = context.annotationInfo

        val beanClasses = annotation.getAttributeOrNull("beanClasses") as Array<Any>? ?: emptyArray()
        val beanNames = annotation.getAttributeOrNull("beanNames") as Array<Any>? ?: emptyArray()

        for (beanClass in beanClasses) {
            val beanName = beanSource.getBeanName(beanClass as AnnotationClassRef)
            if (checkBeanName(bean, context, beanSource, beanName)) {
                return false
            }
        }
        for (beanName in beanNames) {
            if (checkBeanName(bean, context, beanSource, beanName as String)) {
                return false
            }
        }
        return true
    }
}