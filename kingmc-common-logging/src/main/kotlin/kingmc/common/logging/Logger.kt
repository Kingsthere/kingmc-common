package kingmc.common.logging

import net.kyori.adventure.text.Component

/**
 * A logger interface provided by kingmc
 *
 * @author kingsthere
 * @since 0.0.1
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