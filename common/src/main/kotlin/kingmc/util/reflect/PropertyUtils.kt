package kingmc.util.reflect

import kingmc.util.annotation.hasAnnotation
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

/**
 * Find a property in a [KClass] by
 * the name of the property.
 *
 * @author kingsthere
 * @since 0.1
 */
fun KClass<*>.findProperty(name: String): KProperty<*>? {
    return this.members
        .filterIsInstance<KProperty<*>>()
        .find { callable -> callable.name == name }
}

/**
 * Find a mutable property in a [KClass] by
 * the name of the property.
 *
 * @author kingsthere
 * @since 0.1
 */
fun KClass<*>.findMutableProperty(name: String): KMutableProperty<*>? {
    return this.members
        .filterIsInstance<KMutableProperty<*>>()
        .find { callable -> callable.name == name }
}

/**
 * Find a property in a [KClass] that
 * is annotated with any specified annotation.
 *
 * @author kingsthere
 * @since 0.1
 */
inline fun <reified T : Annotation> KClass<*>.findPropertyByAnnotation(): KProperty<*>? {
    return this.findPropertiesByAnnotation<T>().lastOrNull()
}

/**
 * Find a batch of property in a [KClass] that
 * is annotated with any specified annotation.
 *
 * @author kingsthere
 * @since 0.1
 */
inline fun <reified T : Annotation> KClass<*>.findPropertiesByAnnotation(): Collection<KProperty<*>> {
    return this.members
        .filterIsInstance<KProperty<*>>()
        .filter { callable -> callable.hasAnnotation<T>() }
}

/**
 * Find a mutable property in a [KClass] that
 * is annotated with any specified annotation.
 *
 * @author kingsthere
 * @since 0.1
 */
inline fun <reified T : Annotation> KClass<*>.findMutablePropertyByAnnotation(): KMutableProperty<*>? {
    return this.findMutablePropertiesByAnnotation<T>().lastOrNull()
}

/**
 * Find a batch of mutable property in a [KClass] that
 * is annotated with any specified annotation.
 *
 * @author kingsthere
 * @since 0.1
 */
inline fun <reified T : Annotation> KClass<*>.findMutablePropertiesByAnnotation(): Collection<KMutableProperty<*>> {
    return this.members
        .filterIsInstance<KMutableProperty<*>>()
        .filter { callable -> callable.hasAnnotation<T>() }
}

/**
 * The properties that is defined in a [KClass]
 *
 * @since 0.1
 */
val KClass<*>.properties: List<KProperty<*>>
    get() = this.members
        .filterIsInstance<KProperty<*>>()