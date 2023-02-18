package kingmc.common.context.initializer

/**
 * Thrown when a context initializer catch a
 * unhandled exception when trying to initialize
 * a context
 */
open class ContextInitializeException(message: String?, cause: Throwable?) : RuntimeException(message, cause)