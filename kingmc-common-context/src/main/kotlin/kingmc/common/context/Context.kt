package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition
import kingmc.util.format.FormatContextHolder
import kingmc.util.format.Formatted
import java.util.*
import kotlin.reflect.KClass

/**
 * The basic superinterface represent an ioc container
 *
 * @author kingsthere
 * @since 0.1.0
 */
@Formatted
interface Context : FormatContextHolder, Iterable<BeanDefinition> {
    /**
     * The properties of this context
     */
    val properties: Properties

    /**
     * Check if a bean with the given class is available in this context
     *
     * If the given bean is an abstract bean, it checks if there's any available implementation bean
     * of the given abstract bean
     *
     * @param clazz the class of the bean
     * @return `true` if a bean with the given class is available
     */
    fun hasBean(clazz: KClass<*>): Boolean

    /**
     * Check if a bean with the given name is available in this context
     *
     * If the given bean is an abstract bean, it checks if there's any available implementation bean
     * of the given abstract bean
     *
     * @param name the name of the bean
     * @return `true` if a bean with the given name is available
     */
    fun hasBean(name: String): Boolean

    /**
     * Check if any bean definition with class matches the given [clazz] exists
     *
     * @param clazz the class of bean definition to check
     * @return `true` if a bean definition with class matches it exists in this context
     */
    fun hasBeanDefinition(clazz: KClass<*>): Boolean

    /**
     * Check if any bean definition with class matches the given [name] exists
     *
     * @param name the name of bean definition to check
     * @return `true` if a bean definition with class matches exists in this context
     */
    fun hasBeanDefinition(name: String): Boolean

    /**
     * Check if this context has the right access to the given bean
     *
     * @param beanDefinition the bean definition to check
     * @return `true` if this context has the right access to the given bean
     */
    fun canAccess(beanDefinition: BeanDefinition): Boolean

    /**
     * Gets the bean instance with the class that matches the given class
     *
     * @param clazz the class to get bean instance for
     * @return the bean instance of the bean with the given class, `null` if bean with the given class does not exist
     *         in this context
     */
    fun <TBean : Any> getBean(clazz: KClass<out TBean>): TBean?

    /**
     * Gets the bean instance of the bean with the given name
     *
     * @param name the name of the bean
     * @return the bean instance of the bean with the given name, `null` if the bean with the given name does
     *         not exist in this context
     */
    fun getBean(name: String): Any?


    /**
     * Gets the bean definition with the given name
     *
     * @param name the name of the bean definition
     * @return the bean definition with the given, `null` if the bean with the given name does
     *         not exist in this context
     */
    fun getBeanDefinition(name: String): BeanDefinition?


    /**
     * Gets the bean definition with the given class
     *
     * @param clazz the class of the bean definition
     * @return the bean definition with the given class, `null` if the bean with the given class does not exist
     *         in this context
     */
    fun getBeanDefinition(clazz: KClass<*>): BeanDefinition?

    /**
     * Gets the instance of the given bean definition from this context
     *
     * @param beanDefinition the bean definition to instantiate
     * @return bean instance instantiated from this context
     */
    fun getBeanInstance(beanDefinition: BeanDefinition): Any

    /**
     * Gets all bean definitions defined in this context
     */
    fun getBeanDefinitions(): Collection<BeanDefinition>

    /**
     * Return the bean definitions defined in this context as a [Map]
     */
    fun asMap(): Map<String, BeanDefinition>

    /**
     * Remove the given bean definition from this context
     */
    fun remove(beanDefinition: BeanDefinition)

    /**
     * Dispose beans in this context
     */
    fun dispose()

    /**
     * Clear all beans that are registered in this context
     */
    fun clear()
}