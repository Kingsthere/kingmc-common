package kingmc.common.context.format

import kingmc.common.context.Context
import kingmc.util.format.FormatContext
import kotlin.reflect.full.isSubclassOf

/**
 * Format contexts in this context
 *
 * @author kingsthere
 * @since 0.0.7
 */
val Context.formatContexts: List<FormatContext>
    get() = mapNotNull {
        if (it.type.isSubclassOf(FormatContext::class)) {
            getBeanInstance(it) as FormatContext
        } else {
            null
        }
    }