package kingmc.common.logging

import net.kyori.adventure.text.Component
import java.util.logging.Level

class JavaLoggerWrapper(private val logger: JavaLogger) : Logger {
    override fun logInfo(msg: String) {
        this.logger.info(msg)
    }

    override fun logInfo(component: Component) {
        throw UnsupportedOperationException("This JavaLogger is not capable for logging components")
    }

    override fun logWarn(msg: String) {
        this.logger.warning(msg)
    }

    override fun logWarn(component: Component) {
        throw UnsupportedOperationException("This JavaLogger is not capable for logging components")
    }

    /**
     * Send a error message
     *
     * @since 0.0.1
     */
    override fun logError(msg: String) {
        this.logger.log(Level.SEVERE, msg)
    }

    override fun logError(msg: String, throwable: Throwable) {
        this.logger.log(Level.SEVERE, msg, throwable)
    }

    override fun logError(component: Component) {
        throw UnsupportedOperationException("This JavaLogger is not capable for logging components")
    }

    override fun logError(component: Component, throwable: Throwable) {
        throw UnsupportedOperationException("This JavaLogger is not capable for logging components")
    }

    /**
     * Send a debug message
     *
     * @since 0.0.1
     */
    override fun logDebug(msg: String) {
        this.logger.log(Level.FINE, msg)
    }

    override fun logDebug(component: Component) {
        throw UnsupportedOperationException("This JavaLogger is not capable for logging components")
    }

    override fun logTrace(msg: String) {
        this.logger.log(Level.FINER, msg)
    }

    override fun logTrace(component: Component) {
        throw UnsupportedOperationException("This JavaLogger is not capable for logging components")
    }

}