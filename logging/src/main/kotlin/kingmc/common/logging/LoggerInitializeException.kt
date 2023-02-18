package kingmc.common.logging

/**
 * Thrown when a unhandled exception happened while a logger initialize
 *
 * @since 0.0.3
 * @author kingsthere
 */
class LoggerInitializeException(message: String?) : RuntimeException(message)