package kingmc.common.logging

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kingmc.common.application.formatContext
import kingmc.util.KingMCDsl
import kingmc.util.format.Formatted
import kingmc.util.format.formatWithContext
import net.kyori.adventure.text.Component
import kotlin.reflect.KClass

/**
 * The logger manager of current application
 *
 * @since 0.0.9
 * @author kingsthere
 */
val Application.loggers
    get() = (this as LoggerCapableApplication).loggers

/**
 * A Manager serves for logging in one application
 *
 * @since 0.0.9
 * @author kingsthere
 */
interface LoggerManager {
    /**
     * Provide a logger in current application
     *
     * @since 0.0.9
     */
    fun createLogger(): Logger

    /**
     * Provide a named logger in current application
     *
     * @since 0.0.9
     */
    fun createLogger(name: String): Logger

    /**
     * Provide a logger from class in current application
     *
     * @since 0.0.9
     */
    fun createLogger(clazz: Class<*>): Logger

    /**
     * Provide a logger from kotlin class in current application
     *
     * @since 0.0.9
     */
    fun createLogger(clazz: KClass<*>): Logger
}

/**
 * Get the logger from current application
 *
 * @since 0.0.9
 * @author kingsthere
 */
@KingMCDsl
@WithApplication
fun Logger(): Logger {
    try {
        return currentApplication().loggers.createLogger()
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Gets a named logger
 *
 * @since 0.0.9
 * @author kingsthere
 */
@KingMCDsl
@WithApplication
fun Logger(name: String): Logger {
    try {
        return currentApplication().loggers.createLogger(name)
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Log a info log [msg] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun info(@Formatted msg: String) {
    Logger().logInfo(msg.formatWithContext(context = currentApplication().formatContext))
}

/**
 * Log a warn log [msg] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun warn(@Formatted msg: String) {
    Logger().logWarn(msg.formatWithContext(context = currentApplication().formatContext))
}

/**
 * Log a error log [msg] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun error(@Formatted msg: String) {
    Logger().logError(msg.formatWithContext(context = currentApplication().formatContext))
}

/**
 * Log a error log [msg] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun error(@Formatted msg: String, throwable: Throwable) {
    Logger().logError(msg.formatWithContext(context = currentApplication().formatContext), throwable)
}

/**
 * Log a debug log [msg] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun debug(@Formatted msg: String) {
    Logger().logDebug(msg.formatWithContext(context = currentApplication().formatContext))
}

/**
 * Log a trace log [msg] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun trace(@Formatted msg: String) {
    Logger().logTrace(msg.formatWithContext(context = currentApplication().formatContext))
}

/**
 * Log a info log [component] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun info(component: Component) {
    Logger().logInfo(component)
}

/**
 * Log a warn log [component] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun warn(component: Component) {
    Logger().logWarn(component)
}

/**
 * Log a error log [component] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun error(component: Component) {
    Logger().logError(component)
}

/**
 * Log a error log [component] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun error(component: Component, throwable: Throwable) {
    Logger().logError(component, throwable)
}

/**
 * Log a debug log [component] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun debug(component: Component) {
    Logger().logDebug(component)
}

/**
 * Log a trace log [component] from [Logger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@Formatted
@WithApplication
fun trace(component: Component) {
    Logger().logTrace(component)
}