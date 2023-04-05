package kingmc.common.context.format

import kingmc.common.context.Context
import kingmc.common.context.beans.beanClass
import kingmc.util.format.FormatContext
import kotlin.reflect.full.isSubclassOf

/**
 * Format contexts in this context
 *
 * @since 0.0.7
 * @author kingsthere
 */
val Context.formatContexts: List<FormatContext>
    get() = filter { it.beanClass.isSubclassOf(FormatContext::class) }.map { getBeanInstance(it) as FormatContext }