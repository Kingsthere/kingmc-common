package kingmc.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Create a [ThreadLocal] from current thread that delegate this to
 * properties
 *
 * @since 0.0.2
 * @author kingsthere
 * @param T the type of the thread-local variable
 */
fun <T> threadLocal(): ReadWriteProperty<Any?, T?> =
    ThreadLocalDelegate()

/**
 * A delegate delegates a properties for using [ThreadLocal]
 *
 * @since 0.0.2
 * @author kingsthere
 */
class ThreadLocalDelegate<T> : ReadWriteProperty<Any?, T?> {
    /**
     * The thread local instance
     */
    val threadLocal: ThreadLocal<T> = ThreadLocal()

    /**
     * Returns the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @return the property value.
     */
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return threadLocal.get()
    }

    /**
     * Sets the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @param value the value to set.
     */
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            threadLocal.remove()
        } else {
            threadLocal.set(value)
        }
    }

}