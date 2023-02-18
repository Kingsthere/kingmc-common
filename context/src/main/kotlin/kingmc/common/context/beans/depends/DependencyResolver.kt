package kingmc.common.context.beans.depends

import kotlin.reflect.KClass

/**
 * A interface marked the subclasses implement from this interface is
 * to resolve the dependencies that the bean is needed
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface DependencyResolver {
    /**
     * Solve a dependency from the bean class
     *
     * @return the dependency solved
     */
    fun solveDependencyByClass(beanClass: KClass<*>, beanName: String): DependencyDescriptor
}