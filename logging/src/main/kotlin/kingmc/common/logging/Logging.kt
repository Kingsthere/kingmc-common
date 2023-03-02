package kingmc.common.logging

import kingmc.common.application.Application
import kingmc.common.application.WithApplication
import kingmc.common.application.currentApplication
import kingmc.util.KingMCDsl
import kingmc.util.format.Formatted
import net.kyori.adventure.text.Component
import kotlin.reflect.KClass

/**
 * The logger manager of current application
 *
 * @since 0.0.3
 * @author kingsthere
 */
val Application<*>.loggers
    get() = (this as LoggerCapableApplication).loggers

/**
 * A Manager serves for logging in one application
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface LoggerManager {
    /**
     * Provide a logger in current application
     *
     * @since 0.0.3
     */
    fun logger(): Logger

    /**
     * Provide a named logger in current application
     *
     * @since 0.0.3
     */
    fun logger(name: String): Logger

    /**
     * Provide a logger from class in current application
     *
     * @since 0.0.3
     */
    fun logger(clazz: Class<*>): Logger

    /**
     * Provide a logger from kotlin class in current application
     *
     * @since 0.0.3
     */
    fun logger(clazz: KClass<*>): Logger
}

/**
 * Get the logger from current application
 *
 * @since 0.0.3
 * @author kingsthere
 */
@KingMCDsl
@WithApplication
fun logger(): Logger {
    try {
        return currentApplication().loggers.logger()
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Gets a named logger
 *
 * @since 0.0.3
 * @author kingsthere
 */
@KingMCDsl
@WithApplication
fun logger(name: String): Logger {
    try {
        return currentApplication().loggers.logger(name)
    } catch (e: IllegalStateException) {
        throw LoggerInitializeException("Could not load logger, because the logger manager of current application is not set")
    }
}

/**
 * Log a info log [msg] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun info(msg: String? = null) {
    if (msg != null) {
        logger().logInfo(msg)
    }
}

/**
 * Log a warn log [msg] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun warn(msg: String? = null) {
    if (msg != null) {
        logger().logWarn(msg)
    }
}

/**
 * Log a error log [msg] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun error(msg: String? = null) {
    if (msg != null) {
        logger().logError(msg)
    }
}

/**
 * Log a debug log [msg] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun debug(msg: String? = null) {
    if (msg != null) {
        logger().logDebug(msg)
    }
}

/**
 * Log a trace log [msg] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun trace(msg: String? = null) {
    if (msg != null) {
        logger().logTrace(msg)
    }
}

/**
 * Log a info log [component] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun info(component: Component? = null) {
    if (component != null) {
        logger().logInfo(component)
    }
}

/**
 * Log a warn log [component] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun warn(component: Component? = null) {
    if (component != null) {
        logger().logWarn(component)
    }
}

/**
 * Log a error log [component] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun error(component: Component? = null) {
    if (component != null) {
        logger().logError(component)
    }
}

/**
 * Log a debug log [component] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun debug(component: Component? = null) {
    if (component != null) {
        logger().logDebug(component)
    }
}

/**
 * Log a trace log [component] from [logger]
 *
 * @since 0.0.3
 */
@KingMCDsl
@Formatted
@WithApplication
fun trace(component: Component? = null) {
    if (component != null) {
        logger().logTrace(component)
    }
}