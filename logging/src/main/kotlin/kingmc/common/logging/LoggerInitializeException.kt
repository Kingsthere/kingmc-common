package kingmc.common.logging

/**
 * Thrown when an unhandled exception happened while a logger initializes
 *
 * @author kingsthere
 * @since 0.0.3
 */
class LoggerInitializeException(message: String?) : RuntimeException(message)