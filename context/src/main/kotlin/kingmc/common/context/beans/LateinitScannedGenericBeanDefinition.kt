package kingmc.common.context.beans

import kingmc.common.context.Context
import kingmc.common.context.beans.depends.DependencyDescriptor
import kotlin.reflect.KClass

open class LateinitScannedGenericBeanDefinition(
    beanClass: KClass<*>,
    context: Context,
    annotations: List<Annotation>,
    name: String,
    dependencies: DependencyDescriptor,
    scope: BeanScope,
    isAbstract: Boolean,
    deprecated: Boolean,
    primary: Boolean,
    override val lifecycle: Int
) : ScannedGenericBeanDefinition(
    beanClass,
    context,
    annotations,
    name,
    dependencies,
    scope,
    isAbstract,
    deprecated,
    primary
), LateinitBeanDefinition