package kingmc.common.context

import kotlin.reflect.KAnnotatedElement

/**
 * Indicating that a context is capable for checking is elements available
 * in this context
 *
 * @since 0.0.5
 * @author kingsthere
 */
interface ConditionCapableContext : Context {
    /**
     * Test the condition of an element is available
     */
    fun testElementCondition(element: KAnnotatedElement): Boolean
}