package kingmc.common.context.beans.configuration

/**
 * Thrown when a exception happened when trying to load a configuration
 *
 * @since 0.1
 * @author kingsthere
 */
class ConfigurationInitializeException(message: String?, cause: Throwable?) : Exception(message, cause)