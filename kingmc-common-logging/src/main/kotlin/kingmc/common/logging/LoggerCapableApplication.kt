package kingmc.common.logging

import kingmc.common.application.Application

/**
 * An [Application] capable to logging, exposed a [getLogger] for logging
 *
 * @author kingsthere
 * @since 0.0.3
 */
interface LoggerCapableApplication : Application {
    /**
     * The loggers
     */
    val loggers: LoggerManager
}