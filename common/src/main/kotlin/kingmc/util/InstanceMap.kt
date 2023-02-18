package kingmc.util

import com.esotericsoftware.reflectasm.ConstructorAccess
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

/**
 * A utility superinterface express that the subclasses is a utility
 * class to instantiate and cache instantiated instances, it's very
 * similar to a map
 *
 *
 * Here's an example for using `SingletonMap`
 * ```
 * // Get the instance map
 * val instanceMap: InstanceMap = SingletonMap
 *
 * // Instantiate "ExampleClass" using instance map
 * val exampleClassInstance = instanceMap(ExampleClass::class)
 *
 * // "ExampleClass" has already been instantiated, so instead of returning
 * // a new instance, it will return an already created instance
 * val exampleClassInstance2 = instanceMap(ExampleClass::class)
 *
 * exampleClassInstance == exampleClassInstance2
 * ```
 *
 * @since 0.0.1
 * @author kingsthere
 * @sample SingletonMap
 * @sample PrototypeMap
 */
@Utility
interface InstanceMap {
    /**
     * Get an instance of class
     *
     * @param clazz the class to instantiate
     * @param T the type of class to instantiate
     * @throws InstantiateException if an unhandled exception occurred when
     *                              instantiate class
     * @return the instance got
     */
    @Throws(InstantiateException::class)
    operator fun <T : Any> invoke(clazz: KClass<out T>): T
}

/**
 * Extended [InstanceMap] provide singleton instance of classes, return the
 * object instance if the class is defined as an _object_ or a
 * _companion object_ in kotlin
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Utility
open class SingletonMap : InstanceMap {
    // Instantiated classes
    protected val instances: MutableMap<KClass<out Any>, Any> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    @Throws(InstantiateException::class)
    override fun <T : Any> invoke(clazz: KClass<out T>): T {
        if (instances.contains(clazz)) {
            // If the instance is already cached
            // then return the cached instance
            return instances[clazz] as T
        } else {
            try {
                // Try to instantiate the class
                if (clazz.simpleName == "Companion") {
                    // If the class is a companion class
                    return clazz.companionObjectInstance as T
                }
                // Get object instance if the class is an
                // object (in kotlin)
                clazz.objectInstance?.let {
                    instances[clazz] = it
                    return it
                }
                val constructorAccess: ConstructorAccess<out T> = ConstructorAccess.get(clazz.java)
                // Instantiate the class with no-args constructor
                val instantiated = constructorAccess.newInstance()
                if (instantiated != null) {
                    instances[clazz] = instantiated
                    return instantiated
                } else {
                    throw InstantiateException("Unable to instantiate class $clazz cause no-args constructor is not found")
                }
            } catch (ex: NoClassDefFoundError) {
                throw InstantiateException("Unable to instantiate class $clazz", ex)
            } catch (ex: ClassNotFoundException) {
                throw InstantiateException("Unable to instantiate class $clazz", ex)
            } catch (ex: IllegalAccessError) {
                throw InstantiateException("Unable to instantiate class $clazz", ex)
            } catch (ex: IncompatibleClassChangeError) {
                throw InstantiateException("Unable to instantiate class $clazz", ex)
            } catch (ex: ExceptionInInitializerError) {
                throw InstantiateException("Unable to instantiate class $clazz", ex)
            }
        }
    }
}

/**
 * Extended [InstanceMap] only used to provide prototype instances of
 * classes
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Utility
object PrototypeMap : InstanceMap {
    @Throws(InstantiateException::class)
    override fun <T : Any> invoke(clazz: KClass<out T>): T {

        try {
            // Instantiate the class with no-args constructor
            val constructorAccess: ConstructorAccess<out T> = ConstructorAccess.get(clazz.java)
            // Instantiate the class with no-args constructor
            val instantiated = constructorAccess.newInstance()
            if (instantiated != null) {
                return instantiated
            } else {
                throw InstantiateException("Unable to instantiate class $clazz cause no-args constructor is not found")
            }
        } catch (ex: NoClassDefFoundError) {
            throw InstantiateException("Unable to instantiate class $clazz", ex)
        } catch (ex: ClassNotFoundException) {
            throw InstantiateException("Unable to instantiate class $clazz", ex)
        } catch (ex: IllegalAccessError) {
            throw InstantiateException("Unable to instantiate class $clazz", ex)
        } catch (ex: IncompatibleClassChangeError) {
            throw InstantiateException("Unable to instantiate class $clazz", ex)
        } catch (ex: ExceptionInInitializerError) {
            throw InstantiateException("Unable to instantiate class $clazz", ex)
        }
    }
}

/**
 * Instantiate a kotlin class by using [SingletonMap]
 *
 * @since 0.0.2
 * @author kingsthere
 * @see SingletonMap
 */
fun <T : Any> KClass<T>.instance(instanceMap: InstanceMap): T {
    return instanceMap(this)
}

/**
 * Thrown when an exception happened trying to instantiate a class
 *
 * @since 0.0.1
 * @author kingsthere
 */
class InstantiateException : Exception {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message, null)
}