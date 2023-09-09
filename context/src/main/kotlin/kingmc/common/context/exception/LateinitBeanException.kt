package kingmc.common.context.exception

/**
 * Thrown when an exception happened when a bean is loading to an ioc container
 */
class LateinitBeanException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}