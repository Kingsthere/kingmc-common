package kingmc.util.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * Find a no-args constructor in a [KClass]
 *
 * @author kingsthere
 * @since 0.1
 * @see KFunction
 */
fun <R : Any> KClass<R>.findNoArgsConstructor(): KFunction<R>? =
    this.constructors.find { constructor -> constructor.parameters.isEmpty() }

/**
 * Instantiate current class using the default no-args constructor in a [KClass]
 *
 * @author kingsthere
 * @since 0.1
 * @see KFunction
 */
fun <R : Any> KClass<R>.newInstance(): R =
    this.constructors.find { constructor -> constructor.parameters.isEmpty() }?.call()
        ?: throw IllegalArgumentException("No args constructor not defined")