package kingmc.common.structure

/**
 * Throws when a error occurs when a [ClassSource] initializing
 *
 * @since 0.0.1
 * @author kingsthere
 * @see ClassSource
 * @see ProjectContainer
 */
class ProjectInitializeException(message: String?, cause: Throwable?) : Exception(message, cause)