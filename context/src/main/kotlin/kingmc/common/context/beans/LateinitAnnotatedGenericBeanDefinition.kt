package kingmc.common.context.beans

import kingmc.common.context.Context
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

open class LateinitAnnotatedGenericBeanDefinition(
    beanClass: KClass<*>,
    context: Context,
    configurationBean: BeanDefinition,
    beanProvider: KFunction<*>,
    annotations: List<Annotation>,
    name: String,
    scope: BeanScope,
    isAbstract: Boolean,
    deprecated: Boolean,
    primary: Boolean,
    privacy: BeanPrivacy,
    override val lifecycle: Int,
    priority: Byte,
) : AnnotatedGenericBeanDefinition(
    beanClass,
    context,
    configurationBean,
    beanProvider,
    annotations,
    name,
    scope,
    isAbstract,
    deprecated,
    primary,
    privacy, priority
), LateinitBeanDefinition