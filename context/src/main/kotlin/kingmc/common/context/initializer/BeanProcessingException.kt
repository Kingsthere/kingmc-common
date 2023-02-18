package kingmc.common.context.initializer

/**
 * Thrown when trying to process a bean by processors
 *
 * @since 0.0.2
 * @author kingsthere
 */
class BeanProcessingException(message: String?, cause: Throwable?, val bean: Any) : Exception(message, cause)