package kingmc.common.context.process

import kingmc.common.context.Context

/**
 * Extended [Context] expose bean processors defined in this processor context
 *
 * @author kingsthere
 * @since 0.1.0
 */
interface ProcessorContext : Context {
    /**
     * Processors declared in this processor context
     */
    val processors: MutableMap<Int, MutableList<BeanProcessor>>
}