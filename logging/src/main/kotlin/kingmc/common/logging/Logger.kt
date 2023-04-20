package kingmc.common.logging

import net.kyori.adventure.text.Component

/**
 * A kingmc logger is an interface cross every
 * logger from different api (such as slf4j.Logger, java.util) together and use them
 * by the functions set in this interface
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface Logger {
    fun logInfo(msg: String)
    fun logWarn(msg: String)
    fun logError(msg: String)
    fun logError(msg: String, throwable: Throwable)
    fun logDebug(msg: String)
    fun logTrace(msg: String)
    fun logInfo(component: Component)
    fun logWarn(component: Component)
    fun logError(component: Component)
    fun logError(component: Component, throwable: Throwable)
    fun logDebug(component: Component)
    fun logTrace(component: Component)
}