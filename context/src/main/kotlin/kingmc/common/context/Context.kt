package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition
import kingmc.util.errorprone.CanIgnoreReturnValue
import kingmc.util.format.FormatContextHolder
import kingmc.util.format.Formatted
import java.io.Closeable
import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass

/**
 * The basic superinterface represent an ioc container
 *
 * @author kingsthere
 * @since 0.1.0
 */
@Formatted
interface Context : FormatContextHolder, Serializable, Iterable<BeanDefinition>, Closeable {
    /**
     * The properties of this context
     */
    val properties: Properties

    /**
     * Check if a bean definition with class matches the specific [clazz] exists in this context
     *
     * @param clazz the class of bean definition to check
     * @return `true` if a bean definition with class matches is exists in this context
     */
    fun hasBean(clazz: KClass<*>) : Boolean

    /**
     * Check if a bean definition with class matches the specific [name] exists in this context
     *
     * @param name the name of bean definition to check
     * @return `true` if a bean definition with class matches is exists in this context
     */
    fun hasBean(name: String) : Boolean

    /**
     * Gets a bean instance from this context for specific class
     *
     * @param class the class to get bean instance for
     * @return bean instance got
     */
    fun <T : Any> getBean(clazz: KClass<out T>): T

    /**
     * Gets all bean instances from this context for specific class
     *
     * @param class the class to get bean instance for
     * @return bean instances got as a `List`
     */
    fun <T : Any> getBeans(clazz: KClass<out T>): List<T>

    /**
     * Gets a bean instance from this context for name of this bean definition
     *
     * @param name the name to get bean instance for
     * @return bean instance got
     */
    fun getBean(name: String): Any


    /**
     * Gets a bean definition from this context for name of this bean definition
     *
     * @param name the name to get bean instance for
     * @return bean instance got, or `null` if no bean definition found with [name]
     */
    fun getBeanDefinition(name: String): BeanDefinition?


    /**
     * Gets a bean definition from this context for class of this bean definition
     *
     * @param clazz the class to get bean instance for
     * @return bean instance got, or `null` if no bean definition found with [clazz]
     */
    fun getBeanDefinition(clazz: KClass<*>): BeanDefinition?

    /**
     * Gets a bean instance for given bean definition
     *
     * @param beanDefinition BeanDefinition to get bean instance for
     * @return bean instance got for specific bean definition
     */
    fun getBeanInstance(beanDefinition: BeanDefinition): Any

    /**
     * Gets a bean instance from this context for name of this bean definition with
     * specific type
     *
     * @param name the name to get bean instance for
     * @param requiredType the type required for this bean instance
     * @return bean instance got
     */
    fun <T : Any> getBean(name: String, requiredType: KClass<out T>): T

    /**
     * Gets all bean definitions defined in this context
     */
    fun getBeanDefinitions(): Collection<BeanDefinition>

    /**
     * Return the beans definitions defined in this context as a [Map]
     */
    fun asMap(): MutableMap<String, BeanDefinition>

    /**
     * Remove a bean pointed by the name of that bean from this container
     *
     * @parma name the name of the bean to remove
     * @return is the bean removed from this container successfully
     */
    @CanIgnoreReturnValue
    fun remove(name: String)
}