package kingmc.common.context.exception

import kotlin.reflect.KClass

/**
 * Thrown if a specifies bean definition is not found when needed
 *
 * @author kingsthere
 * @since 0.0.1
 */
class NoSuchBeanException : BeansException {
    /**
     * The bean name of the bean trying to find
     */
    private val beanName: String?

    /**
     * The bean type of the bean trying to find
     */
    private val beanType: KClass<*>?

    constructor(message: String?, beanName: String? = null, beanType: KClass<*>? = null) : super(message) {
        this.beanName = beanName
        this.beanType = beanType
    }
    constructor(message: String?, cause: Throwable?, beanName: String? = null, beanType: KClass<*>? = null) : super(message, cause) {
        this.beanName = beanName
        this.beanType = beanType
    }

    override fun toString(): String {
        val s = this.javaClass.name
        val message = this.localizedMessage
        return if (message != null) "$s(beanName=$beanName,beanClass=$beanType): $message" else s
    }
}