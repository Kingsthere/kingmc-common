package kingmc.common.structure

/**
 * Throws when a error occurs when a [Project] initializing
 *
 * @since 0.0.1
 * @author kingsthere
 * @see Project
 * @see ProjectContainer
 */
class ProjectInitializeException(message: String?, cause: Throwable?) : Exception(message, cause)