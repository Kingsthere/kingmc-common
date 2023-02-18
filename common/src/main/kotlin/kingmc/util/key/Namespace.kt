package kingmc.util.key

import org.intellij.lang.annotations.Pattern


/**
 * Something that has a namespace.
 *
 * @since 0.0.1
 */
interface Namespaced {
    /**
     * Gets the namespace.
     *
     * @return the namespace
     * @since 0.0.1
     */
    @Pattern(KeyImpl.NAMESPACE_PATTERN)
    fun namespace(): String
}
