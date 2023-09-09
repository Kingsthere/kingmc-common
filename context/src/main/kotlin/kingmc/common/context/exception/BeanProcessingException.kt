package kingmc.common.context.exception

import kingmc.common.context.process.BeanProcessor

/**
 * Thrown when trying to process a bean by processors
 *
 * @author kingsthere
 * @since 0.0.2
 */
class BeanProcessingException(message: String?, cause: Throwable?, val bean: Any?, val processor: BeanProcessor) :
    Exception(message, cause)