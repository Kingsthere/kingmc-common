package kingmc.util

import kingmc.util.builder.Buildable
import kingmc.util.key.Key
import kingmc.util.key.Keyed
import java.io.Serializable
import kotlin.reflect.KClass

/**
 * Represent a Pointer, Pointer is a type of
 * common identifier to resources. An implement
 * of [Pointer] must override [hashCode] and [equals]
 * method by default
 *
 * @since 0.0.1
 * @author kingsthere
 * @param T the type of the resource this pointer is targeting
 *          to keep the typesafe
 * @see Keyed
 */
interface Pointer<T : Any> : Keyed, Cloneable, Serializable {
    /**
     * The key of this pointer
     *
     * @since 0.0.1
     */
    override val key: Key

    /**
     * The class of the type of pointer
     *
     * @since 0.0.1
     * @see T
     */
    val type: KClass<T>
}

/**
 * Represent a Pointer that could target to a
 * [Set] or a [List] of resources
 *
 * @since 0.0.1
 * @author kingsthere
 * @param T the elements in the collection
 * @see Pointer
 */
interface CollectionPointer<T : Any> : Pointer<Collection<T>> {
    /**
     * The class of the type of elements in the collection
     *
     * @since 0.0.1
     */
    val elementType: KClass<T>

    override val type: KClass<Collection<T>>
}

/**
 * Represent a set of resources linked
 * with [Pointer]
 *
 * @since 0.0.1
 * @author kingsthere
 * @param T the type of pointers this could accept
 */
interface Pointers<T : Any> : Cloneable, Serializable, Iterable<Pointer<out T>> {
    /**
     * The resources in this pointers
     *
     * @see Collection
     * @see Pointer
     * @see T
     * @since 0.0.1
     */
    val values: Collection<T>

    /**
     * Get a value from this object by the
     * pointer
     *
     * @throws PointerNotSupportException if the pointer is not supported
     * @throws PointerTypeException if the type is incorrect
     * @since 0.0.1
     */
    @Throws(PointerException::class)
    operator fun <E : T> get(pointer: Pointer<E>): E

    /**
     * Check if this pointers set have a resource
     * that is linked to a pointer
     *
     * @return if the pointer is supported
     * @since 0.0.1
     */
    fun <E : T> has(pointer: Pointer<E>): Boolean

    /**
     * Build a Pointers instance
     *
     * @see Buildable.Builder
     * @since 0.0.1
     */
    interface Builder<T : Any> : Buildable.Builder<Pointers<T>> {
        /**
         * Add a pointer to this builder
         *
         * @since 0.0.1
         */
        fun <E : T> with(pointer: Pointer<E>, value: E): Builder<T>
    }
}


/**
 * A modifiable [Pointers] allow you to modify the
 * content of pointers
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface MutablePointers<T : Any> : Pointers<T> {
    /**
     * Put a value into this pointer
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    fun <E : T> put(pointer: Pointer<out E>, value: E)

    /**
     * Put a batch of value into this pointer
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    fun <E : T> putAll(values: Map<out Pointer<out E>, E>)

    /**
     * Put a batch of value into this pointer from
     * the pointers set
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    fun <E : T> putAll(values: Pointers<out E>)

    /**
     * Remove a value from this pointers
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    fun <E : T> remove(pointer: Pointer<out E>)

    /**
     * Clear all pointers from this pointers
     *
     * @since 0.0.1
     */
    fun clear()
}

/**
 * Simple implement of [Pointers]
 *
 * @since 0.0.1
 * @author kingsthere
 * @see Pointers
 */
open class SimplePointers<T : Any>(
    /**
     * The value map
     */
    val value: MutableMap<Pointer<out T>, T>
) : MutablePointers<T> {

    /**
     * Get a value from this object by the
     * pointer
     *
     * @throws PointerNotSupportException if the pointer is not supported
     * @throws PointerTypeException if the type is incorrect
     * @since 0.0.1
     */
    @Throws(PointerNotSupportException::class, PointerTypeException::class)
    override fun <E : T> get(pointer: Pointer<E>): E {
        if (!this.value.containsKey(pointer)) {
            throw PointerNotSupportException("Pointer not support $pointer")
        }
        val resolvedPointer = resolvePointer(pointer) ?: throw PointerNotSupportException("Pointer not support $pointer")
        try {
            @Suppress("UNCHECKED_CAST")
            return resolvedPointer as E
        } catch (exception: ClassCastException) {
            throw PointerTypeException("Incorrect pointer type of $resolvedPointer")
        }
    }

    protected fun resolvePointer(pointer: Pointer<*>): Any? =
        this.value[pointer]

    /**
     * Check if this pointers set have a resource
     * that is linked to a pointer
     *
     * @return if the pointer is supported
     * @since 0.0.1
     */
    override fun <E : T> has(pointer: Pointer<E>): Boolean =
        this.value.containsKey(pointer)

    open class Builder<T : Any> : Pointers.Builder<T> {
        /**
         * The value map of the pointers current
         * building
         */
        val value: MutableMap<Pointer<out T>, T> = LinkedHashMap()

        /**
         * Add a pointer to this builder
         *
         * @since 0.0.1
         */
        override fun <E : T> with(pointer: Pointer<E>, value: E): Pointers.Builder<T> {
            this.value[pointer] = value
            return this
        }

        /**
         * Builds.
         *
         * @return the built thing
         * @since 0.0.1
         */
        override fun build(): MutablePointers<T> =
            SimplePointers(this.value)

    }

    /**
     * Put a value into this pointer
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    override fun <E : T> put(pointer: Pointer<out E>, value: E) {
        this.value[pointer] = value
    }

    /**
     * Put a batch of value into this pointer
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    override fun <E : T> putAll(values: Map<out Pointer<out E>, E>) {
        this.value.putAll(values)
    }

    /**
     * Remove a value from this poiner
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    override fun <E : T> remove(pointer: Pointer<out E>) {
        this.value.remove(pointer)
    }

    /**
     * Put a batch of value into this pointer from
     * the pointers set
     *
     * @since 0.0.1
     * @see Pointer
     * @see T
     * @see E
     */
    override fun <E : T> putAll(values: Pointers<out E>) {
        this.value.putAll((values as SimplePointers).value)
    }

    /**
     * Clear all pointers from this pointers
     *
     * @since 0.0.1
     */
    override fun clear() {
        this.value.clear()
    }

    /**
     * The resources in this pointers
     *
     * @see Pointer
     * @see T
     * @since 0.0.1
     */
    override val values: Collection<T>
        get() = this.value.values

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<Pointer<out T>> =
        this.value.keys.iterator()

}

/**
 * Something that can retrieve values based on a given [Pointer]
 *
 * @since 0.0.1
 * @author kingsthere
 * @see Pointer
 * @see Pointers
 * @param T the type of pointers this set could accept
 */
interface Pointered<T : Any> {
    /**
     * Get a value from this object by the
     * pointer
     *
     * @throws PointerNotSupportException if the pointer is not supported
     * @since 0.0.1
     */
    @Throws(PointerException::class)
    operator fun <E : T> get(pointer: Pointer<E>): E =
        this.pointers[pointer]

    /**
     * Check if this pointers set have a resource
     * that is linked to a pointer
     *
     * @return if the pointer is supported
     * @since 0.0.1
     */
    fun <E : T> has(pointer: Pointer<E>): Boolean =
        this.pointers.has(pointer)

    /**
     * The pointers for this object
     *
     * @return the pointers
     * @since 4.8.0
     */
    val pointers: Pointers<T>
}

/**
 * Create and return a simple pointers builder
 *
 * @since 0.0.1
 * @author kingsthere
 * @see SimplePointers
 * @see SimplePointers.Builder
 * @see Pointers
 * @see Pointer
 */
fun <T : Any> simplePointers() : SimplePointers.Builder<T> =
    SimplePointers.Builder()

/**
 * Represent an exception targeted to pointer
 *
 * @since 0.0.1
 * @author kingsthere
 */
open class PointerException(message: String) : RuntimeException(message)

/**
 * Thrown when trying to get a resource by a pointer that
 * the resource is not supported
 *
 * @since 0.0.1
 * @author kingsthere
 * @see PointerException
 */
class PointerNotSupportException(message: String) : PointerException(message)

/**
 * Thrown when trying to get a resource by a pointer that
 * the resource but the type of the resource is different as
 * the type you expected
 *
 * @since 0.0.1
 * @author kingsthere
 * @see PointerException
 */
class PointerTypeException(message: String) : PointerException(message)

/**
 * Represent a pointer is experimental
 *
 * @since 0.0.1
 * @author kingsthere
 */
@RequiresOptIn("Represent a pointer is experimental. It may be changed in the future without notice.")
@Retention
@Target(AnnotationTarget.CLASS)
annotation class ExperimentalPointer