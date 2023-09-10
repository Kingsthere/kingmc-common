package kingmc.common.context.annotation

import kingmc.common.context.beans.LoadingBeanDefinition

/**
 * Represents a condition tester to test if the bean is available for the given context
 *
 * @author kingsthere
 * @since 0.1.2
 */
fun interface Condition {
    /**
     * Test if the given bean is available for the given context
     */
    fun test(bean: LoadingBeanDefinition, context: ConditionContext): Boolean
}