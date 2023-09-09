package kingmc.common.context.beans

import kingmc.common.context.Context
import kotlin.reflect.KClass

/**
 * Implemented [BeanDefinition] represent a registered abstract scanned bean
 *
 * @author kingsthere
 * @since 0.0.1
 */
open class AbstractScannedBeanDefinition(
    type: KClass<*>,
    context: Context,
    annotations: List<Annotation>,
    name: String,
    scope: BeanScope,
    deprecated: Boolean,
    primary: Boolean,
    privacy: BeanPrivacy,
    priority: Byte,
    implementations: Collection<BeanDefinition>
) : ScannedBeanDefinition(type, context, annotations, name, scope, deprecated, primary, privacy, priority, true, implementations) {
    /**
     * Return `true` if this bean is an abstract bean
     */
    override fun isAbstract(): Boolean = true

    override fun isOpen(): Boolean = true

    override fun toString(): String {
        return "AbstractScannedGenericBeanDefinition(name='$name',type='$type',scope=$scope)"
    }
}

/**
 * Extended [AbstractScannedBeanDefinition], this class represents a lateinit [AbstractScannedBeanDefinition]
 */
open class AbstractLateinitScannedBeanDefinition(
    type: KClass<*>,
    context: Context,
    annotations: List<Annotation>,
    name: String,
    scope: BeanScope,
    deprecated: Boolean,
    primary: Boolean,
    privacy: BeanPrivacy,
    override val lifecycle: Int,
    priority: Byte,
    implementations: Collection<BeanDefinition>
) : AbstractScannedBeanDefinition(
    type,
    context,
    annotations,
    name,
    scope,
    deprecated,
    primary,
    privacy,
    priority,
    implementations
), LateinitBeanDefinition {
    override fun toString(): String {
        return "AbstractLateinitScannedBeanDefinition(lifecycle=$lifecycle) ${super.toString()}"
    }
}