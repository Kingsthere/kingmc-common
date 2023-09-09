package kingmc.common.context.beans.configuration

import kingmc.common.context.Context
import kingmc.common.context.annotation.Configuration
import kingmc.common.context.beans.BeanDefinition
import kingmc.util.annotation.hasAnnotation

/**
 * Get all configuration beans that are present in this context
 *
 * @author kingsthere
 * @since 0.0.1
 */
val Context.configurationBeans: List<BeanDefinition>
    get() = filter { it.type.hasAnnotation<Configuration>() }