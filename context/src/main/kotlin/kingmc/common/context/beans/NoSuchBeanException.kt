package kingmc.common.context.beans

/**
 * Exception thrown when trying to get a bean from a container
 * but the bean is not defined in that context, you should use
 * [kingmc.common.context.Context.hasBean] before using get method
 * from the context to prevent this exception thrown
 *
 * @since 0.0.1
 * @author kingsthere
 */
class NoSuchBeanException : BeansException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}