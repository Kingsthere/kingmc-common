package kingmc.common.context.beans

import kingmc.common.context.Context
import kotlin.reflect.KClass

/**
 * Implemented [BeanDefinition] represent a registered scanned bean
 *
 * @author kingsthere
 * @since 0.0.1
 */
open class ScannedBeanDefinition(
    override val type: KClass<*>,
    override val context: Context,
    override val annotations: List<Annotation>,
    override val name: String,
    override val scope: BeanScope,
    override val deprecated: Boolean,
    override val primary: Boolean,
    override val privacy: BeanPrivacy,
    override val priority: Byte,
    val isOpened: Boolean = false,
    val implementations: Collection<BeanDefinition>? = null
) : AnnotationAwareBeanDefinition {
    init {
        if (isOpened) {
            requireNotNull(implementations) { "Implementations must be specified for an open bean" }
        }
    }

    /**
     * Return `true` if this bean is an abstract bean
     */
    override fun isAbstract(): Boolean = false

    /**
     * Returns `true` if this is an open bean
     */
    override fun isOpen(): Boolean = isOpened

    override fun implementations(): Collection<BeanDefinition> =
        implementations ?: throw UnsupportedOperationException("This is not an abstract/open bean")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScannedBeanDefinition

        if (context != other.context) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = context.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "ScannedBeanDefinition(name='$name',type='$type',scope=$scope)"
    }
}

/**
 * Extended [ScannedBeanDefinition], this class represents a lateinit [ScannedBeanDefinition]
 */
open class LateinitScannedBeanDefinition(
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
    isOpen: Boolean = false,
    implementations: Collection<BeanDefinition>? = null
) : ScannedBeanDefinition(
    type,
    context,
    annotations,
    name,
    scope,
    deprecated,
    primary,
    privacy,
    priority,
    isOpen,
    implementations
), LateinitBeanDefinition {
    override fun toString(): String {
        return "LateinitScannedBeanDefinition(lifecycle=$lifecycle) ${super.toString()}"
    }
}