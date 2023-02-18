package kingmc.common.logging

import kingmc.common.application.Application

/**
 * An [Application] capable to logging, exposed a [logger] for logging
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface LoggerCapableApplication {
    /**
     * The loggers
     */
    val loggers: LoggerManager
}