package kingmc.common.context.beans

import com.google.errorprone.annotations.Immutable
import kingmc.common.context.Context
import kotlin.reflect.KClass

/**
 * A `BeanDefinition` describes an instance of Bean, includes the common properties of bean
 *
 * @author kingsthere
 * @since 0.1.1
 */
@Immutable
interface BeanDefinition {
    /**
     * The name of this bean
     *
     * **Note: ** The name of beans must be _unique_ in a context
     *
     * @since 0.0.1
     */
    val name: String

    /**
     * The context that loads this bean definition
     *
     * @since 0.0.4
     */
    val context: Context

    /**
     * The class of this bean definition
     *
     * @since 0.1.1
     */
    val type: KClass<*>

    /**
     * The scope of this bean
     *
     * @since 0.0.2
     */
    val scope: BeanScope

    /**
     * The bean privacy of this bean
     *
     * @since 0.1.0
     */
    val privacy: BeanPrivacy

    /**
     * `true` if this bean is deprecated
     *
     * @since 0.0.7
     */
    val deprecated: Boolean

    /**
     * `true` if this bean is primary
     *
     * @since 0.0.7
     */
    val primary: Boolean

    /**
     * The priority of this bean definition
     */
    val priority: Byte
        get() = 0

    /**
     * Returns `true` if this is an open bean
     */
    fun isOpen(): Boolean

    /**
     * Return `true` if this bean is an abstract bean
     */
    fun isAbstract(): Boolean

    /**
     * Return the implementation beans of this bean definition
     * if this bean is an abstract bean ([isAbstract])
     */
    fun implementations(): Collection<BeanDefinition>
}