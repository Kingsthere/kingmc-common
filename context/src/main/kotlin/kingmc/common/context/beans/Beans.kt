package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.HierarchicalContext
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Find beans defined in this context that matches the specified
 * type
 *
 * @since 0.0.2
 * @author kingsthere
 */
fun Context.findBeansByType(type: KClass<*>): List<BeanDefinition> {
    return this.filter { bean ->
        bean.beanClass.isSubclassOf(type)
    }
}

/**
 * Find a bean defined in this context that matches the specified
 * type
 *
 * @since 0.0.2
 * @author kingsthere
 */
fun Context.findBeanByType(type: KClass<*>): BeanDefinition? {
    return this.find { bean -> bean.beanClass.isSubclassOf(type) }
}

/**
 * Find singleton beans defined in this context that matches the specified
 * type
 *
 * @since 0.0.2
 * @author kingsthere
 */
fun Context.findSingletonBeansByType(type: KClass<*>): List<BeanDefinition> {
    return this.filter { bean ->
        bean.beanClass.isSubclassOf(type) && bean.isSingleton()
    }
}

/**
 * Get all singleton beans from the context for prevent
 * extra instantiate of prototype beans
 *
 * @since 0.0.2
 * @author kingsthere
 */
fun Context.getSingletonBeans(): List<Any> {
    return filter { it.isSingleton() }.map { getBeanInstance(it) }
}

/**
 * Get all protected singleton beans from the context for prevent
 * extra instantiate of prototype beans
 *
 * @since 0.0.2
 * @author kingsthere
 */
fun HierarchicalContext.getProtectedSingletonBeans(): List<Any> {
    return getProtectedBeans().filter { it.isSingleton() }.map { getBeanInstance(it) }
}

/**
 * Gets all protected singleton beans from the context for prevent
 * extra instantiate of prototype beans
 *
 * @since 0.0.5
 * @author kingsthere
 */
fun HierarchicalContext.getOwningSingletonBeans(): List<Any> {
    return getProtectedBeans().filter { it.isSingleton() }.map { getBeanInstance(it) }
}