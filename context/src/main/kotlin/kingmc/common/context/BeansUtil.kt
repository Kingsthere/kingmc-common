package kingmc.common.context

import kingmc.common.context.annotation.Component
import kingmc.util.Utility
import kingmc.util.annotation.getAnnotation
import kingmc.util.annotation.hasAnnotation
import java.util.*
import kotlin.reflect.KClass

/**
 * Utility class for beans container
 *
 * @since 0.0.2
 * @author kingsthere
 */
@Utility
object BeansUtil {
    /**
     * Get the bean name from a class, returned the bean id
     * is the simple name of class and the first
     * letter is lowercase, such as
     * ```
     * @Component //("")
     * class TestBean {
     *     // ...
     * }
     * // The bean name is "testBean" (The class name with first character lowercase)
     * ```
     *
     *
     * If the bean present a [Component] annotation and the property
     * [Component.name] is present then the bean name is the name set, for
     * example:
     * ```
     * @Component("test")
     * class TestBean {
     *     // ...
     * }
     * // So the bean name is "test", not "testBean"
     * ```
     *
     * @since 0.0.2
     */
    fun getBeanName(clazz: KClass<*>): String {
        // Check if the bean has annotation @Component
        if (clazz.hasAnnotation<Component>()) {
            return try {
                val component = clazz.getAnnotation<Component>()!!
                component.name.ifEmpty {
                    clazz.simpleName!!.replaceFirstChar { it.lowercase(Locale.getDefault()) }
                }
            } catch (e: Exception) {
                clazz.simpleName!!.replaceFirstChar { it.lowercase(Locale.getDefault()) }
            }
        }
        return clazz.simpleName!!.replaceFirstChar { it.lowercase(Locale.getDefault()) }
    }
}