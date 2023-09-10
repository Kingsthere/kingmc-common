package kingmc.common.context.beans

import kingmc.common.context.Context
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * Implemented [BeanDefinition] represent a registered annotated bean
 *
 * @author kingsthere
 * @since 0.0.1
 */
open class AnnotatedBeanDefinition(
    override val type: KClass<*>,
    val provider: BeanDefinition,
    val providerKFunction: KFunction<*>,
    override val context: Context,
    override val annotations: List<Annotation>,
    override val name: String,
    override val scope: BeanScope,
    override val deprecated: Boolean,
    override val primary: Boolean,
    override val privacy: BeanPrivacy,
    override val priority: Byte
) : AnnotationAwareBeanDefinition {
    /**
     * Return `true` if this bean is an abstract bean
     */
    override fun isAbstract(): Boolean = false

    /**
     * Returns `true` if this is an open bean
     */
    override fun isOpen(): Boolean = false

    /**
     * Return the implementation beans of this bean definition
     * if this bean is an abstract bean ([isAbstract])
     */
    override fun implementations(): Collection<BeanDefinition> {
        throw UnsupportedOperationException("This is not an abstract bean")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractScannedBeanDefinition

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
        return "AnnotatedBeanDefinition(name='$name',type='$type',scope=$scope)"
    }
}

/**
 * Extended [ScannedBeanDefinition], this class represents a lateinit [AnnotatedBeanDefinition]
 */
open class LateinitAnnotatedBeanDefinition(
    type: KClass<*>,
    provider: BeanDefinition,
    providerKFunction: KFunction<*>,
    context: Context,
    annotations: List<Annotation>,
    name: String,
    scope: BeanScope,
    deprecated: Boolean,
    primary: Boolean,
    privacy: BeanPrivacy,
    override val lifecycle: Int,
    priority: Byte,
) : AnnotatedBeanDefinition(
    type,
    provider,
    providerKFunction,
    context,
    annotations,
    name,
    scope,
    deprecated,
    primary,
    privacy,
    priority
), LateinitBeanDefinition {
    override fun toString(): String {
        return "LateinitAnnotatedBeanDefinition(lifecycle=$lifecycle) ${super.toString()}"
    }
}