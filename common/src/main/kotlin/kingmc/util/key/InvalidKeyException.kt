package kingmc.util.key

/**
 * This exception is thrown when an invalid namespace and/or value has been detected while creating a [Key].
 *
 * @since 0.0.1
 */
class InvalidKeyException(
    private val keyNamespace: String,
    private val keyValue: String,
    message: String?
) : RuntimeException(message) {
    /**
     * Gets the invalid key, as a string.
     *
     * @return a key
     * @since 0.0.1
     */
    fun keyNamespace(): String {
        return keyNamespace
    }

    /**
     * Gets the invalid key, as a string.
     *
     * @return a key
     * @since 0.0.1
     */
    fun keyValue(): String {
        return keyValue
    }
}