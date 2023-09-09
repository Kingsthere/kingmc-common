package kingmc.common.context.annotation

import kingmc.common.context.BeanSource
import kingmc.common.context.beans.LoadingBeanDefinition

/**
 * Describe the context when checking the condition of a bean
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface ConditionContext {
    /**
     * Check if the given bean definition is available in the current context
     */
    fun isBeanAvailable(loadingBeanDefinition: LoadingBeanDefinition): Boolean

    /**
     * Gets the bean source for the loading bean definition
     */
    fun getBeanSource(): BeanSource
}