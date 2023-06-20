package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition
import kingmc.common.context.beans.BeansException
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
 * @since 0.0.1
 */
@Formatted
interface Context : FormatContextHolder, Serializable, Iterable<BeanDefinition>, Closeable {
    /**
     * The properties of this context
     */
    val properties: Properties

    /**
     * Check a bean with specified type is existed
     * in this ioc container
     *
     * @param clazz the class of the bean
     * @since 0.0.1
     * @return is the bean exist
     */
    fun <T : Any> hasBean(clazz: KClass<out T>) : Boolean

    /**
     * Check a bean with specified name is existed
     * in this ioc container
     *
     * @param name the class of the bean
     * @since 0.0.1
     * @return is the bean exist
     */
    fun hasBean(name: String) : Boolean

    /**
     * Get a bean with specified type in this container
     * by the type of that bean
     *
     * @param clazz the class
     * @param T the type of bean
     * @since 0.0.1
     * @return the bean got
     */
    @Throws(BeansException::class)
    fun <T : Any> getBean(clazz: KClass<out T>): T

    /**
     * Get all beans with specified type in this container
     * by the type of that bean
     *
     * @param clazz the class
     * @param T the type of bean
     * @since 0.0.1
     * @return the beans got with [clazz]
     */
    @Throws(BeansException::class)
    fun <T : Any> getBeans(clazz: KClass<out T>): List<T>

    /**
     * Get a bean with specified name in this container
     * by the name of that bean
     *
     * @param name the bean name
     * @see BeanDefinition.name
     * @since 0.0.1
     * @return the bean got
     */
    @Throws(BeansException::class)
    fun getBean(name: String): Any

    /**
     * Get a bean definition with specified name in this container
     * by the name of that bean
     *
     * @param name the bean name
     * @see BeanDefinition.name
     * @since 0.1.0
     * @return the bean definition got
     */
    @Throws(BeansException::class)
    fun getBeanDefinition(name: String): BeanDefinition?

    /**
     * Get a bean definition with specified type in this container
     * by the name of that bean
     *
     * @param clazz the bean class
     * @param T the type of bean
     * @see BeanDefinition.name
     * @since 0.1.0
     * @return the bean definition got
     */
    @Throws(BeansException::class)
    fun getBeanDefinition(clazz: KClass<*>): BeanDefinition?

    /**
     * Gets a bean instance from [BeanDefinition]
     *
     * @param beanDefinition the bean definition
     * @param T the type of bean
     * @since 0.0.1
     * @return the bean got
     */
    @Throws(BeansException::class)
    fun getBeanInstance(beanDefinition: BeanDefinition): Any

    /**
     * Get a bean in this container
     * by the name of that bean with required type
     *
     * @param name the bean name
     * @param T the type of bean
     * @see BeanDefinition.name
     * @since 0.0.1
     * @return the bean got
     */
    @Throws(BeansException::class)
    fun <T : Any> getBean(name: String, requiredType: KClass<out T>): T

    /**
     * Get all beans instances that in this container
     * and return as a [Collection]
     *
     * @since 0.0.1
     * @see Collection
     * @return the beans got
     */
    fun getBeans(): Collection<Any>

    /**
     * Return the beans defined in this context as
     * a [Map]
     */
    fun asMap(): MutableMap<String, BeanDefinition>

    /**
     * Remove a bean from this container
     *
     * @param T the type of bean
     * @return is the bean removed from this container successfully
     * @since 0.0.1
     */
    @CanIgnoreReturnValue
    fun <T : Any> remove(type: KClass<out T>)

    /**
     * Remove a bean pointed by the name of that bean from this container
     *
     * @param T the type of bean
     * @return is the bean removed from this container successfully
     * @since 0.0.1
     */
    @CanIgnoreReturnValue
    fun remove(name: String)
}