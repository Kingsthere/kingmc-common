package kingmc.common.context.exception

/**
 * Thrown when an exception happened trying to populate dependencies
 * to a loaded bean
 *
 * @author kingsthere
 * @since 0.0.2
 */
class PopulateBeansException : ContextInitializeException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message, null)
}