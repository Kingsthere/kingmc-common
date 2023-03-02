package kingmc.common.logging

import kingmc.common.logging.slf4j.Slf4jLoggerWrapper
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
    override fun logger(): Logger =
        defaultLogger

    /**
     * Provide a named logger in current application
     *
     * @since 0.0.3
     */
    override fun logger(name: String): Logger =
        Slf4jLoggerWrapper(LoggerFactory.getLogger(name))

    /**
     * Provide a logger from class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: Class<*>): Logger =
        Slf4jLoggerWrapper(LoggerFactory.getLogger(clazz))

    /**
     * Provide a logger from kotlin class in current application
     *
     * @since 0.0.3
     */
    override fun logger(clazz: KClass<*>): Logger =
        Slf4jLoggerWrapper(LoggerFactory.getLogger(clazz.java))
}