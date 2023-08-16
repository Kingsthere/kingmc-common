package kingmc.common.context.beans

import kingmc.common.context.Context
import kotlin.reflect.KClass

open class LateinitScannedGenericBeanDefinition(
    beanClass: KClass<*>,
    context: Context,
    annotations: List<Annotation>,
    name: String,
    scope: BeanScope,
    isAbstract: Boolean,
    deprecated: Boolean,
    primary: Boolean,
    privacy: BeanPrivacy,
    override val lifecycle: Int,
    priority: Byte,
) : ScannedGenericBeanDefinition(
    beanClass,
    context,
    annotations,
    name,
    scope,
    isAbstract,
    deprecated,
    primary,
    privacy,
    priority
), LateinitBeanDefinition