package kingmc.common.context.beans

/**
 * Thrown when a exception happened when a bean is loading to a ioc container
 */
class BeanInitializeException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}