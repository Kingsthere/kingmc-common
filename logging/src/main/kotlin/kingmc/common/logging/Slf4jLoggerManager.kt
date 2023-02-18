package kingmc.common.logging

import kotlin.reflect.KClass

class Slf4jLoggerManager(
    private val defaultLogger: Slf4jLoggerWrapper
) : LoggerManager {

    /**
     * Provide a logger in current application
     *
     * @since 0.0.3
     */
    override fun logger(): LoggerWrapper =
        defaultLogger

    /**
     * Provide a named logger in current application
     *
     * @since 0.0.3
     */
    override fun logger(name: String): LoggerWrapper =
        Slf4jLoggerWrapper(ComponentLogger.logger(name))

    /**
     * Provide a logger from class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: Class<*>): LoggerWrapper =
        Slf4jLoggerWrapper(ComponentLogger.logger(clazz))

    /**
     * Provide a logger from kotlin class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: KClass<*>): LoggerWrapper =
        Slf4jLoggerWrapper(ComponentLogger.logger(clazz.java))
}