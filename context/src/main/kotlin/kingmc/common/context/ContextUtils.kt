package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition
import kotlin.reflect.KAnnotatedElement

/**
 * Check if a bean(definition) is protected
 *
 * @since 0.0.4
 * @author kingsthere
 */
fun HierarchicalContext.isProtectedBean(beanDefinition: BeanDefinition): Boolean {
    return this.getProtectedBeans().contains(beanDefinition)
}

/**
 * Check an element conditions by this context
 *
 * @since 0.0.5
 * @author kingsthere
 */
fun Context.checkElementCondition(element: KAnnotatedElement): Boolean {
    return if (this is ConditionCapableContext) {
        testElementCondition(element)
    } else {
        true
    }
}