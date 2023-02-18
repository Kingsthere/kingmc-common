package kingmc.common.context.beans

/**
 * Abstract superclass for all exceptions thrown in the beans package and subpackages.
 * Note that this is a runtime (unchecked) exception. Beans exceptions are usually
 * fatal; there is no reason for them to be checked.
 *
 * @since 0.0.1
 * @author kingsthere
 */
open class BeansException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}