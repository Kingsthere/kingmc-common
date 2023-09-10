package kingmc.common.context.exception

/**
 * Thrown when context is initializing
 */
open class ContextInitializeException(message: String?, cause: Throwable?) : RuntimeException(message, cause)