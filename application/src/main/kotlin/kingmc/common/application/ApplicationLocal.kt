package kingmc.common.application

import java.util.concurrent.atomic.AtomicInteger

/**
 * An utility class to create a isolated variable between applications
 *
 * @since 0.1.0
 * @author kingsthere
 * @param T the kind of value this application local stores
 */
class ApplicationLocal<T> {
    companion object {
        /**
         * The next hash code to be given out. Updated atomically. Starts at
         * zero.
         */
        private val nextHashCode = AtomicInteger()

        /**
         * The difference between successively generated hash codes - turns
         * implicit sequential `ApplicationLocal` IDs into near-optimally spread
         * multiplicative hash values for power-of-two-sized tables.
         */
        private const val HASH_INCREMENT = 0x61c88647
    }

    /**
     * Returns the next hash code.
     */
    private fun nextHashCode(): Int {
        return nextHashCode.getAndAdd(HASH_INCREMENT)
    }

    private val applicationLocalHashCode = nextHashCode()

    /**
     * Set the value that this application local stores
     *
     * @param value the value to set to
     */
    @WithApplication
    fun set(value: T) {
        currentApplication().applicationLocalMap.set(applicationLocalHashCode, value)
    }

    /**
     * Remove the value that this application local stores
     */
    @WithApplication
    fun remove() {
        currentApplication().applicationLocalMap.remove(applicationLocalHashCode)
    }

    /**
     * Get the value that this application local stores
     */
    @WithApplication
    fun get(): T? {
        return currentApplication().applicationLocalMap.get(applicationLocalHashCode)
    }

}