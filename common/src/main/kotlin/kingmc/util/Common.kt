package kingmc.util

import java.util.function.Supplier

/**
 * Package the supplier specified with [lazy]
 *
 * @author kingsthere
 * @since 0.0.1
 */
fun <T> lazySupplier(supplier: () -> T): Supplier<T> {
    return object : Supplier<T> {

        val obj by lazy {
            supplier()
        }

        override fun get(): T {
            return obj
        }
    }
}