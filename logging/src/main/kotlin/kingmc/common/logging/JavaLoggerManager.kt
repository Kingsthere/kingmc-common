package kingmc.common.logging

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class JavaLoggerManager(
    private val defaultLogger: JavaLoggerWrapper
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
        Slf4jLoggerWrapper(LoggerFactory.getLogger(name))

    /**
     * Provide a logger from class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: Class<*>): LoggerWrapper =
        Slf4jLoggerWrapper(LoggerFactory.getLogger(clazz))

    /**
     * Provide a logger from kotlin class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: KClass<*>): LoggerWrapper =
        Slf4jLoggerWrapper(LoggerFactory.getLogger(clazz.java))
}