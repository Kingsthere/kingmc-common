package kingmc.common.context.beans.configuration

import kingmc.util.annotation.hasAnnotation
import kingmc.common.context.Context
import kingmc.common.context.annotation.Configuration
import kingmc.common.context.beans.BeanDefinition
import kingmc.common.context.beans.beanClass

/**
 * Get all configuration beans that is present in this context
 *
 * @since 0.0.1
 * @author kingsthere
 */
val Context.configurationBeans: List<BeanDefinition>
    get() = filter { it.beanClass.hasAnnotation<Configuration>() }