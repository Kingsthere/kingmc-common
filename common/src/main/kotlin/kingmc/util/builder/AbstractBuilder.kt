package kingmc.util.builder

import org.jetbrains.annotations.Contract
import java.util.function.Consumer

/**
 * A builder.
 *
 * @param R the type to be built
 * @author kingsthere
 * @since 0.0.1
 */
fun interface AbstractBuilder<R> {
    /**
     * Builds.
     *
     * @return the built thing
     * @since 4.10.0
     */
    @Contract(value = "-> new", pure = true)
    fun build(): R

    companion object {
        /**
         * Configures `builder` using `consumer` and then builds.
         *
         * @param builder  the builder
         * @param consumer the builder consume
         * @param <R>      the type to be built
         * @param <B>      the builder type
         * @return the built thing
        </B></R> */
        @Contract(mutates = "param1")
        fun <R, B : AbstractBuilder<R>?> configureAndBuild(builder: B, consumer: Consumer<in B?>?): R {
            consumer?.accept(builder)
            return builder!!.build()
        }
    }
}