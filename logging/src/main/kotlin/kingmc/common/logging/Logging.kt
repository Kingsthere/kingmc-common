package kingmc.common.logging

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kingmc.util.KingMCDsl
import net.kyori.adventure.text.Component
import kotlin.reflect.KClass

/**
 * The logger manager of current application
 *
 * @author kingsthere
 * @since 0.0.9
 */
val Application.loggers
    get() = (this as LoggerCapableApplication).loggers

/**
 * A Manager serves for logging in one application
 *
 * @author kingsthere
 * @since 0.0.9
 */
interface LoggerManager {
    /**
     * Returns the default logger
     *
     * @since 0.0.9
     */
    fun defaultLogger(): Logger

    /**
     * Provide a named logger in the current application
     *
     * @since 0.0.9
     */
    fun defaultLogger(name: String): Logger

    /**
     * Provide a logger from class in the current application
     *
     * @since 0.0.9
     */
    fun defaultLogger(clazz: Class<*>): Logger

    /**
     * Provide a logger from kotlin class in the current application
     *
     * @since 0.0.9
     */
    fun defaultLogger(clazz: KClass<*>): Logger
}

/**
 * Get the logger from the current application
 *
 * @author kingsthere
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun getLogger(): Logger {
    try {
        return currentApplication().loggers.defaultLogger()
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Gets a named logger
 *
 * @author kingsthere
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun getLogger(name: String): Logger {
    try {
        return currentApplication().loggers.defaultLogger(name)
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Gets a logger for the given class
 *
 * @author kingsthere
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun getLogger(clazz: KClass<*>): Logger {
    try {
        return currentApplication().loggers.defaultLogger(clazz)
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Log a info log [msg] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun info(msg: String) {
    getLogger().logInfo(msg)
}

/**
 * Log a warn log [msg] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun warn(msg: String) {
    getLogger().logWarn(msg)
}

/**
 * Log a error log [msg] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun error(msg: String) {
    getLogger().logError(msg)
}

/**
 * Log a error log [msg] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun error(msg: String, throwable: Throwable) {
    getLogger().logError(msg, throwable)
}

/**
 * Log a debug log [msg] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun debug(msg: String) {
    getLogger().logDebug(msg)
}

/**
 * Log a trace log [msg] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun trace(msg: String) {
    getLogger().logTrace(msg)
}

/**
 * Log a info log [component] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun info(component: Component) {
    getLogger().logInfo(component)
}

/**
 * Log a warn log [component] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun warn(component: Component) {
    getLogger().logWarn(component)
}

/**
 * Log a error log [component] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun error(component: Component) {
    getLogger().logError(component)
}

/**
 * Log a error log [component] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun error(component: Component, throwable: Throwable) {
    getLogger().logError(component, throwable)
}

/**
 * Log a debug log [component] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun debug(component: Component) {
    getLogger().logDebug(component)
}

/**
 * Log a trace log [component] from [getLogger]
 *
 * @since 0.0.9
 */
@KingMCDsl
@WithApplication
fun trace(component: Component) {
    getLogger().logTrace(component)
}