package kingmc.common.logging.slf4j

import kingmc.common.logging.Logger
import kingmc.common.logging.LoggerManager
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import kotlin.reflect.KClass

class Slf4jLoggerManager(
    private val defaultLogger: Slf4jLoggerWrapper
) : LoggerManager {

    /**
     * Provide a logger in current application
     *
     * @since 0.0.3
     */
    override fun logger(): Logger =
        defaultLogger

    /**
     * Provide a named logger in current application
     *
     * @since 0.0.3
     */
    override fun logger(name: String): Logger =
        Slf4jLoggerWrapper(ComponentLogger.logger(name))

    /**
     * Provide a logger from class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: Class<*>): Logger =
        Slf4jLoggerWrapper(ComponentLogger.logger(clazz))

    /**
     * Provide a logger from kotlin class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: KClass<*>): Logger =
        Slf4jLoggerWrapper(ComponentLogger.logger(clazz.java))
}