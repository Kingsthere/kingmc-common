package kingmc.util.builder

import org.jetbrains.annotations.Contract
import java.util.function.Consumer

/**
 * Something that can be built.
 *
 * @param <R> the type that can be built
 * @param <B> the builder type
 * @author kingsthere
 * @since 0.0.1
 */
interface Buildable<R, B : Buildable.Builder<R>?> {
    /**
     * Create a builder from this thing.
     *
     * @return a builder
     * @since 0.0.1
     */
    @Contract(value = "-> new", pure = true)
    fun toBuilder(): B

    /**
     * A builder.
     *
     * @param R the type to be built
     * @since 0.0.1
     */
    interface Builder<R> {
        /**
         * Builds.
         *
         * @return the built thing
         * @since 0.0.1
         */
        @Contract(value = "-> new", pure = true)
        fun build(): R
    }

    companion object {
        /**
         * Configures `builder` using `consumer` and then builds.
         *
         * @param builder  the builder
         * @param consumer the builder consume
         * @param R        the type to be built
         * @param R        the builder type
         * @return the built thing
         * @since 0.0.1
         */
        @Contract(mutates = "param1")
        fun <R : Buildable<R, B>?, B : Builder<R>?> configureAndBuild(builder: B, consumer: Consumer<in B>?): R {
            consumer?.accept(builder)
            return builder!!.build()
        }
    }
}