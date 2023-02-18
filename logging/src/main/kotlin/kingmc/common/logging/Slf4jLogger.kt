package kingmc.common.logging

import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.slf4j.Logger

typealias Slf4jLogger = Logger
typealias ComponentLogger = ComponentLogger

/**
 * Create and configure a slf4j logger manager
 *
 * @since 0.0.3
 * @author kingsthere
 */
fun slf4jLoggers(defaultLogger: Slf4jLogger): Slf4jLoggerManager =
    Slf4jLoggerManager(Slf4jLoggerWrapper(defaultLogger))