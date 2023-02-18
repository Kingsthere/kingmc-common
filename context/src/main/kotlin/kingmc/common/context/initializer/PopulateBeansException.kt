package kingmc.common.context.initializer

/**
 * Thrown when an exception happened trying to populate dependencies
 * to a loaded bean
 *
 * @since 0.0.2
 * @author kingsthere
 */
class PopulateBeansException : ContextInitializeException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message, null)
}