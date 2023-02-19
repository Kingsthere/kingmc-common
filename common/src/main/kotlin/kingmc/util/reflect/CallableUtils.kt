package kingmc.util.reflect

import kingmc.util.annotation.hasAnnotation
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KType

/**
 * Find a callable in a [KClass] by the
 * name of the callable
 *
 * @since 0.1
 * @author kingsthere
 */
fun KClass<*>.findCallable(name: String): KCallable<*>? {
    return this.members.find { callable -> callable.name == name }
}

/**
 * Find a parameter from current callable by the
 * name of the callable
 *
 * @since 0.1
 * @author kingsthere
 */
fun KCallable<*>.findParameter(name: String): KParameter? =
    this.parameters.find { it.name == name }

/**
 * Find all parameter from current callable by the
 * type of the callable
 *
 * @since 0.1
 * @author kingsthere
 */
fun KCallable<*>.findParametersByAnnotation(type: KType): List<KParameter> =
    this.parameters.filter { it.type == type }

/**
 * Find all parameter from current callable by the
 * annotation of the callable
 *
 * @since 0.1
 * @author kingsthere
 */
inline fun <reified T : Annotation> KCallable<*>.findParametersByAnnotation(): List<KParameter> {
    return this.parameters.filter { it.hasAnnotation<T>() }
}

/**
 * Find one parameter from current callable by the
 * type of the callable
 *
 * @since 0.1
 * @author kingsthere
 */
fun KCallable<*>.findParameter(type: KType): KParameter? =
    this.parameters.find { it.type == type }