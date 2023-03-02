package kingmc.common.context.resource

/**
 * Thrown if a exception occurred loading a resource
 *
 * @since 0.0.6
 * @author kingsthere
 */
class ResourceLoadException(message: String?, cause: Throwable?) : Exception(message, cause)