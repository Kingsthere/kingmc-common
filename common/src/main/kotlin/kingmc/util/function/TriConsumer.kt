package kingmc.util.function

/**
 * Represents an operation that accepts **three** input arguments and returns no
 * result. This is the two-arity specialization of [java.util.function.Consumer].
 * Unlike most other functional interfaces, `BiConsumer` is expected
 * to operate via side-effects.
 *
 *
 * This is a [functional interface](package-summary.html)
 * whose functional method is [.accept]
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 *
 * @see java.util.function.Consumer * @since 0.0.1
</U></T> */
fun interface TriConsumer<T, U, R> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param r the third input argument
     */
    fun accept(t: T, u: U, r: R)
}