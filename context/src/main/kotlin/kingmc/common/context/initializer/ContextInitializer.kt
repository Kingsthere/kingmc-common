package kingmc.common.context.initializer

import kingmc.common.context.Context
import kingmc.common.context.beans.BeanDefinition
import kingmc.common.structure.ClassSource
import java.util.function.Predicate
import kotlin.reflect.KAnnotatedElement

/**
 * An superinterface responsible for loading [Context]s
 *
 * @since 0.0.5
 * @author kingsthere
 */
interface ContextInitializer {
    /**
     * The context this initializer is initializing
     */
    val context: Context

    /**
     * Add a filter to filter beans
     */
    fun addBeanFilter(filter: Predicate<BeanDefinition>)

    /**
     * Add a element filter
     */
    fun addElementFilter(filter: Predicate<KAnnotatedElement>)

    /**
     * Add a bean source([ClassSource]) to this context
     */
    fun addSource(classSource: ClassSource)

    /**
     * Invoke this context initializer and finish initializing of target context
     */
    operator fun invoke()

    /**
     * Called to dispose (close) the context
     */
    fun dispose()

}