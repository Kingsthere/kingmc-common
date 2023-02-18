package kingmc.common.logging

@Deprecated(
    message = "Since 0.0.3, use application.logger instead to use detached loggers from application",
    replaceWith = ReplaceWith("Any.info", "kingmc.common.logging.info")
)
// The logger wrapper using to
// adapt the application environment
lateinit var loggerWrapper: LoggerWrapper

/**
 * To show the debug logs
 */
const val LOG_DEBUG: Boolean = true

/**
 * Send a info to logger
 *
 * @since 0.0.1
 */
@Deprecated(
    message = "Since 0.0.3, use Any.info to log from current application's logger",
    replaceWith = ReplaceWith("Any.info", "kingmc.common.logging.info")
)
fun infoByWrapper(msg: String? = null) {
    if (!msg.isNullOrEmpty()) {
        loggerWrapper.logInfo(msg)
    }
}

/**
 * Send a warning message to logger
 *
 * @since 0.0.1
 */
@Deprecated(
    message = "Since 0.0.3, use Any.info to log from current application's logger",
    replaceWith = ReplaceWith("Any.info", "kingmc.common.logging.info")
)
fun warnByWrapper(msg: String? = null) {
    if (!msg.isNullOrEmpty()) {
        loggerWrapper.logWarn(msg)
    }
}

/**
 * Send a error message to logger
 *
 * @since 0.0.1
 */
@Deprecated(
    message = "Since 0.0.3, use Any.info to log from current application's logger",
    replaceWith = ReplaceWith("Any.info", "kingmc.common.logging.info")
)
fun errorByWrapper(msg: String? = null) {
    if (!msg.isNullOrEmpty()) {
        loggerWrapper.logError(msg)
    }
}

/**
 * Send a debug message to logger
 *
 * @since 0.0.1
 */
@Deprecated(
    message = "Since 0.0.3, use Any.info to log from current application's logger",
    replaceWith = ReplaceWith("Any.info", "kingmc.common.logging.info")
)
fun debugByWrapper(msg: String? = null) {
    if (!msg.isNullOrEmpty()) {
        if (LOG_DEBUG) {
            loggerWrapper.logInfo(msg)
        }
    }
}