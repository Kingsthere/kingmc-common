package kingmc.common.logging.slf4j

import kingmc.common.logging.Logger
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.logger.slf4j.ComponentLogger

class Slf4jLoggerWrapper(private val logger: Slf4jLogger) : Logger {
    override fun logInfo(msg: String) {
        this.logger.info(msg)
    }

    override fun logInfo(component: Component) {
        (this.logger as ComponentLogger).info(component)
    }

    override fun logWarn(msg: String) {
        this.logger.warn(msg)
    }

    override fun logWarn(component: Component) {
        (this.logger as ComponentLogger).warn(component)
    }

    override fun logError(msg: String) {
        this.logger.error(msg)
    }

    override fun logError(component: Component) {
        (this.logger as ComponentLogger).error(component)
    }

    override fun logDebug(msg: String) {
        this.logger.debug(msg)
    }

    override fun logDebug(component: Component) {
        (this.logger as ComponentLogger).debug(component)
    }

    override fun logTrace(msg: String) {
        this.logger.trace(msg)
    }

    override fun logTrace(component: Component) {
        (this.logger as ComponentLogger).trace(component)
    }

}