package kingmc.common.context

import kingmc.common.context.beans.BeanDefinition
import kingmc.common.context.beans.LoadingBeanDefinition

/**
 * An interface responsible to provide beans to `ContextInitializer`
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface BeanSource {
    /**
     * Start to load beans from this bean source
     */
    fun load()

    /**
     * Add a parent bean source to this bean source
     */
    fun addParent(parent: BeanSource)


    /**
     * Get already prepared bean definitions from this bean source
     */
    fun getPreparedBeanDefinitions(): List<BeanDefinition>

    /**
     * Finish loading and get loaded bean definitions from this bean source
     */
    fun finishLoading(context: Context): List<BeanDefinition>

    /**
     * Gets a loading bean definition with the given name from this bean source
     *
     * @param name the name of the bean to get
     * @return loading bean definition created by this bean source, or `null` if
     *         bean with the given name of not found in this bean source
     */
    fun getLoadingBeanDefinition(name: String): LoadingBeanDefinition?

    /**
     * Check if a loading bean definition with the given name it exists in this bean source
     *
     * @param name the name of the bean to check
     * @return `true` if the bean definition with the given name is existing in this bean source
     */
    fun hasLoadingBeanDefinition(name: String): Boolean
}