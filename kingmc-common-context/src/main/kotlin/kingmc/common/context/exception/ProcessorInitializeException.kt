package kingmc.common.context.exception

/**
 * Thrown when an exception happened when processor initialized
 *
 * @author kingsthere
 * @since 0.1
 */
class ProcessorInitializeException(message: String?, cause: Throwable?) : ContextInitializeException(message, cause)