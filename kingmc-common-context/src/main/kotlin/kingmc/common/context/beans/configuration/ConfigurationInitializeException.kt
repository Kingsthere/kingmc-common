package kingmc.common.context.beans.configuration

/**
 * Thrown when an exception happened when trying to load a configuration
 *
 * @author kingsthere
 * @since 0.1.0
 */
class ConfigurationInitializeException(message: String?, cause: Throwable?) : Exception(message, cause)