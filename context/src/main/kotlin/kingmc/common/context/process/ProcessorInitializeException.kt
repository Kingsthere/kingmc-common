package kingmc.common.context.process

import kingmc.common.context.initializer.ContextInitializeException

/**
 * Thrown when a exception happened when processor initialized
 *
 * @since 0.1
 * @author kingsthere
 */
class ProcessorInitializeException(message: String?, cause: Throwable?) : ContextInitializeException(message, cause)