package kingmc.common.logging

import net.kyori.adventure.text.Component
import java.util.logging.Level

class JavaLoggerWrapper(private val logger: JavaLogger) : LoggerWrapper {
    override fun logInfo(msg: String) {
        this.logger.info(msg)
    }

    override fun logInfo(component: Component) {
        TODO("Not yet implemented")
    }

    override fun logWarn(msg: String) {
        this.logger.warning(msg)
    }

    override fun logWarn(component: Component) {
        TODO("Not yet implemented")
    }

    /**
     * Send a error message
     *
     * @since 0.0.1
     */
    override fun logError(msg: String) {
        this.logger.log(Level.SEVERE, msg)
    }

    override fun logError(component: Component) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun logTrace(msg: String) {
        this.logger.log(Level.FINER, msg)
    }

    override fun logTrace(component: Component) {
        TODO("Not yet implemented")
    }

}