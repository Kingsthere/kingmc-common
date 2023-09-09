package kingmc.common.context.exception

/**
 * Thrown when accessing a bean without right permission
 */
class IllegalBeanAccessException(message: String?) : Exception(message)