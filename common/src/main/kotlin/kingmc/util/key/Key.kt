package kingmc.util.key

import org.intellij.lang.annotations.Pattern
import org.jetbrains.annotations.VisibleForTesting
import java.util.*


/**
 * An identifying object used to fetch and/or store unique objects.
 *
 *
 * A key consists of:
 *  + namespace
 *    - in most cases this should be your plugin or organization name</dd>
 *  + value
 *    - what this key leads to, e.g "translations" or "entity.firework_rocket.blast"</dd>
 *
 *
 *
 * Valid characters for namespaces are [`[a-z0-9_.-]`](https://regexr.com/5ibbm).
 *
 *
 * Valid characters for values are [`[a-z0-9/._-]`](https://regexr.com/5if3m).
 *
 *
 * Some examples of possible custom keys:
 *
 *  +  my_plugin:translations
 *  +  my_plugin:weapon.amazing-weapon_damage-attribute
 *  +  my_organization:music.song_1
 *  +  my_organization:item.magic_button
 *
 *
 * @since 0.0.1
 * @author kingsthere
 * @see Namespaced
 */
interface Key : Comparable<Key>, Namespaced {
    /**
     * Gets the namespace.
     *
     * @return the namespace
     * @since 0.0.1
     */
    override fun namespace(): String

    /**
     * Gets the value.
     *
     * @return the value
     * @since 0.0.1
     */
    fun value(): String

    /**
     * Returns the string representation of this key.
     *
     * @return the string representation
     * @since 0.1.1
     */
    @Deprecated("Use toString() instead")
    fun asString(): String

    override operator fun compareTo(other: Key): Int {
        return comparator().compare(this, other)
    }

    companion object {
        /**
         * Gets the comparator to compare keys.
         *
         *
         * The [value][.value] is compared first, followed by the [namespace][.namespace].
         *
         * @return a comparator for keys
         * @since 4.10.0
         */
        fun comparator(): Comparator<in Key> {
            return KeyImpl.COMPARATOR
        }
    }
}


internal class KeyImpl(namespace: String, value: String) : Key {
    private val namespace: String
    private val value: String

    init {
        if (!namespaceValid(namespace)) throw InvalidKeyException(
            namespace,
            value,
            String.format(
                "Non [a-z0-9_.-] character in namespace of Key[%s]",
                asString(namespace, value)
            )
        )
        if (!valueValid(value)) throw InvalidKeyException(
            namespace,
            value,
            String.format(
                "Non [a-z0-9/._-] character in value of Key[%s]",
                asString(namespace, value)
            )
        )
        this.namespace = Objects.requireNonNull(namespace, "namespace")
        this.value = Objects.requireNonNull(value, "value")
    }

    override fun namespace(): String {
        return namespace
    }

    override fun value(): String {
        return value
    }

    @Deprecated("Use toString() instead")
    override fun asString(): String {
        return asString(namespace, value)
    }

    override fun toString(): String {
        return this.asString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is KeyImpl) return false

        if (namespace != other.namespace) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        var result = namespace.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }


    companion object {
        val COMPARATOR: Comparator<in Key> =
            Comparator.comparing { obj: Key -> obj.value() }
                .thenComparing { obj: Key -> obj.namespace() }
        const val NAMESPACE_PATTERN = "[a-z0-9_\\-.]+"
        const val VALUE_PATTERN = "[a-z0-9_\\-./]+"
        @VisibleForTesting
        fun namespaceValid(namespace: String): Boolean {
            var i = 0
            val length = namespace.length
            while (i < length) {
                if (!validNamespaceChar(namespace[i].code)) {
                    return false
                }
                i++
            }
            return true
        }

        @VisibleForTesting
        fun valueValid(value: String): Boolean {
            var i = 0
            val length = value.length
            while (i < length) {
                if (!validValueChar(value[i].code)) {
                    return false
                }
                i++
            }
            return true
        }

        private fun validNamespaceChar(value: Int): Boolean {
            return value == '_'.code || value == '-'.code || value >= 'a'.code && value <= 'z'.code || value >= '0'.code && value <= '9'.code || value == '.'.code
        }

        private fun validValueChar(value: Int): Boolean {
            return value == '_'.code || value == '-'.code || value >= 'a'.code && value <= 'z'.code || value >= '0'.code && value <= '9'.code || value == '/'.code || value == '.'.code
        }

        private fun asString(namespace: String, value: String): String {
            return "$namespace:$value"
        }
    }
}


/**
 * The namespace for Minecraft.
 *
 * @since 0.0.1
 */
const val MINECRAFT_NAMESPACE = "minecraft"

/**
 * The namespace for KingMC.
 *
 * @since 0.0.1
 */
const val KINGMC_NAMESPACE = "kingmc"

/**
 * Creates a key.
 *
 *
 * This will parse `string` as a key, using `character` as a separator between the namespace and the value.
 *
 *
 * The namespace is optional. If you do not provide one (for example, if you provide `player` or `character + "player"`
 * as the string) then [.MINECRAFT_NAMESPACE] will be used as a namespace and `string` will be used as the value,
 * removing the provided separator character if necessary.
 *
 * @param string the string
 * @param character the character that separates the namespace from the value
 * @return the key
 * @throws InvalidKeyException if the namespace or value contains an invalid character
 * @since 0.0.1
 */
@JvmOverloads
fun Key(string: String, character: Char = ':'): Key {
    val index = string.indexOf(character)
    val namespace = if (index >= 1) string.substring(0, index) else MINECRAFT_NAMESPACE
    val value = if (index >= 0) string.substring(index + 1) else string
    return KeyImpl(namespace, value)
}

/**
 * Creates a key.
 *
 * @param namespaced the namespace source
 * @param value the value
 * @return the key
 * @throws InvalidKeyException if the namespace or value contains an invalid character
 * @since 4.4.0
 */
fun Key(namespaced: Namespaced, @Pattern(KeyImpl.VALUE_PATTERN) value: String): Key {
    return KeyImpl(namespaced.namespace(), value)
}

/**
 * Creates a key.
 *
 * @param namespace the namespace
 * @param value the value
 * @return the key
 * @throws InvalidKeyException if the namespace or value contains an invalid character
 * @since 0.0.1
 */
fun Key(
    @Pattern(KeyImpl.NAMESPACE_PATTERN) namespace: String,
    @Pattern(KeyImpl.VALUE_PATTERN) value: String
): Key {
    return KeyImpl(namespace, value)
}