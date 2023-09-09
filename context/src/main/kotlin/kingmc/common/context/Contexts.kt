package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition
import kingmc.common.context.exception.NoSuchBeanException

/**
 * Try to get the bean with the given name from this context, throw exception if the bean
 * with the given name does not exist in this context
 *
 * @param name the name of the bean
 * @author kingsthere
 * @since 0.1.2
 */
fun Context.getBeanOrThrow(name: String): Any {
    return getBean(name) ?: throw NoSuchBeanException("No such bean with name $name", beanName = name)
}

/**
 * Try to get the bean with the given bean type from this context, throw exception if the
 * bean with the given type does not exist in this context
 *
 * @param TBean the type of the bean
 * @return the bean that matches the given type found
 * @author kingsthere
 * @since 0.1.2
 */
inline fun <reified TBean : Any> Context.getBeanOrThrow(): TBean {
    val clazz = TBean::class
    return getBean(clazz) ?: throw NoSuchBeanException(
        "No such bean with class ${clazz.qualifiedName}",
        beanType = clazz
    )
}

/**
 * Gets the bean source of this context
 */
val Context.beanSource: BeanSource
    get() = (this as BeanSourceApplicationContext).beanSource

/**
 * Check if a bean(definition) is protected
 *
 * @author kingsthere
 * @since 0.0.4
 */
@Deprecated("Just check beanDefinition.context", ReplaceWith("beanDefinition.context == this"))
fun HierarchicalContext.isProtectedBean(beanDefinition: BeanDefinition): Boolean {
    return beanDefinition.context == this
}

/**
 * Try to find the best implementation bean for the given bean definition from this context
 *
 * @param beanDefinition the bean definition to find implementation bean for
 * @return the bean implementation definition
 * @author kingsthere
 * @since 0.1.2
 */
fun Context.findImplementationBean(beanDefinition: BeanDefinition): BeanDefinition? =
    beanDefinition.implementations().find { canAccess(it) }

/**
 * Try to find the best overridden bean for the given bean definition from this context
 *
 * @param beanDefinition the bean definition to find overridden bean for
 * @return the bean overridden definition, or itself if no such bean overrides the given bean
 * @author kingsthere
 * @since 0.1.2
 */
fun Context.findOverriddenBean(beanDefinition: BeanDefinition): BeanDefinition =
    beanDefinition.implementations().find { canAccess(it) } ?: beanDefinition