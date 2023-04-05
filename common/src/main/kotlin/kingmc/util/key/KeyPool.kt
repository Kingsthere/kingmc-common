package kingmc.util.key

import java.util.*

/**
 * Represent a key pool to cache the keys that is already
 * created multiple of times
 *
 * @since 0.0.1
 * @author kingsthere
 */
object KeyPool {
    private val value: MutableMap<Int, Key> = WeakHashMap()

    fun get(namespace: String, value: String): Key {
        val hashCode = Objects.hash(namespace, value)
        return if (this.value.containsKey(hashCode)) (
            this.value[hashCode]!!
        ) else {
            val key = KeyImpl(namespace, value)
            this.value[hashCode] = key
            key
        }
    }
}