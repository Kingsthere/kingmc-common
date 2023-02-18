package kingmc.util

import java.util.function.Supplier

/**
 * Package the supplier specified with [lazy]
 *
 * @since 0.0.1
 * @author kingsthere
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