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

/**
 * Find a bean in this context by [T], returns `null` if this
 * context can't find any bean by [T]
 *
 * @since 0.0.6
 * @author kingsthere
 * @return The bean found
 */
inline fun <reified T : Any> Context.findBean(): Any? {
    val clazz = T::class
    return if (hasBean(clazz)) {
        getBean(clazz)
    } else {
        null
    }
}

/**
 * Find all beans in this context by [T]
 *
 * @since 0.0.6
 * @author kingsthere
 * @return The bean found as a `List`
 */
inline fun <reified T : Any> Context.findBeans(): List<Any> {
    return getBeans(T::class)
}