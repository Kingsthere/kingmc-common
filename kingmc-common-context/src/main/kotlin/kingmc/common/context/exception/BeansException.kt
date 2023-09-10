package kingmc.common.context.exception

/**
 * Abstract superclass for all exceptions thrown in the bean package and subpackages.
 * Note that this is a runtime (unchecked) exception. Beans exceptions are usually
 * fatal; there is no reason for them to be checked
 *
 * @author kingsthere
 * @since 0.0.1
 */
open class BeansException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}