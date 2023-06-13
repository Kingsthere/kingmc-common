package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.beans.depends.DependencyDescriptor
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

open class LateinitAnnotatedGenericBeanDefinition(
    beanClass: KClass<*>,
    context: Context,
    configurationBean: BeanDefinition,
    beanProvider: KFunction<*>,
    annotations: List<Annotation>,
    name: String,
    dependencies: DependencyDescriptor,
    scope: BeanScope,
    isAbstract: Boolean,
    deprecated: Boolean,
    primary: Boolean,
    override val lifecycle: Int
) : AnnotatedGenericBeanDefinition(
    beanClass,
    context,
    configurationBean,
    beanProvider,
    annotations,
    name,
    dependencies,
    scope,
    isAbstract,
    deprecated,
    primary
), LateinitBeanDefinition