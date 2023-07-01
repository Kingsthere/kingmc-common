package kingmc.util.reflect

import kingmc.util.annotation.hasAnnotation
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions

/**
 * Find a function in a [KClass] by the
 * name of the callable.
 *
 * @since 0.1
 * @author kingsthere
 * @see KFunction
 */
fun KClass<*>.findFunction(name: String): KFunction<*>? =
    this.findFunctions(name).lastOrNull()

/**
 * Check if a class has a function
 *
 * @since 0.1
 * @author kingsthere
 * @see KFunction
 */
fun KClass<*>.hasFunction(name: String): Boolean {
    return this.findFunctions(name).isNotEmpty()
}

/**
 * Find a batch of function in a [KClass] by the
 * annotation of the callable.
 *
 * @since 0.1
 * @author kingsthere
 * @see KFunction
 */
fun KClass<*>.findFunctions(name: String): Collection<KFunction<*>> =
    this.functions
        .filter { callable -> callable.name == name }

/**
 * Find a function in a [KClass] by the
 * name of the callable.
 *
 * @since 0.1
 * @author kingsthere
 * @see KFunction
 */
inline fun <reified T : Annotation> KClass<*>.findFunctionByAnnotation(): KFunction<*>? =
    this.findFunctionsByAnnotation<T>().lastOrNull()

/**
 * Check if a class has a function that is annotated
 * with specified annotation
 *
 * @since 0.1
 * @author kingsthere
 * @see KFunction
 */
inline fun <reified T : Annotation> KClass<*>.functionExistByAnnotation(): Boolean {
    return this.findFunctionsByAnnotation<T>().isNotEmpty()
}

/**
 * Find a batch of function in a [KClass] by the
 * annotation of the callable
 *
 * @since 0.1
 * @author kingsthere
 * @see KFunction
 */
inline fun <reified T : Annotation> KClass<*>.findFunctionsByAnnotation(): Collection<KFunction<*>> =
    this.functions
        .filter { callable -> callable.hasAnnotation<T>() }

