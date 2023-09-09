package kingmc.common.context.annotation

import kingmc.common.context.PropertyBeanSource
import kingmc.common.context.beans.LoadingBeanDefinition
import kingmc.common.context.source.ClassGraphBeanSource
import kingmc.common.context.source.ClassGraphConditionContext

/**
 * [ConditionalOnProperty] condition implementation
 */
object ConditionOnProperty : Condition {
    override fun test(bean: LoadingBeanDefinition, context: ConditionContext): Boolean {
        val beanSource = context.getBeanSource()
        // Validate
        require(context is ClassGraphConditionContext)
        require(beanSource is PropertyBeanSource) { "ConditionOnProperty only supports for ClassGraphBeanSource(s)" }

        val annotation = context.annotationInfo

        val property = annotation.getAttribute("property") as String
        val value = annotation.getAttribute("value") as String

        return beanSource.properties.getProperty(property) == value
    }
}