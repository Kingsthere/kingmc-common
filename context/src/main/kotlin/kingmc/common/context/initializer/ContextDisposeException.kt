package kingmc.common.context.initializer

/**
 * Thrown when a context initializer catch a
 * unhandled exception when trying to dispose
 * a context
 */
class ContextDisposeException(message: String?, cause: Throwable?) : RuntimeException(message, cause)