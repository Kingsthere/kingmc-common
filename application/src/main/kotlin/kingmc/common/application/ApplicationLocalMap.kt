package kingmc.common.application

import java.util.concurrent.ConcurrentHashMap

/**
 * A map in `Application` used to store values for `ApplicationLocal` instances
 *
 * @since 0.1.0
 * @author kingsthere
 * @see ApplicationLocal
 */
class ApplicationLocalMap {
    private val values: MutableMap<Int, Any?> = ConcurrentHashMap()

    fun <T> set(applicationLocal: Int, value: T) {
        values[applicationLocal] = value
    }

    fun remove(applicationLocal: Int) {
        values.remove(applicationLocal)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(applicationLocal: Int): T? {
        return values[applicationLocal] as T
    }
}