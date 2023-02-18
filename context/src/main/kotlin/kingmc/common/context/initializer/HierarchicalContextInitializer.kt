package kingmc.common.context.initializer

import kingmc.common.context.Context
import kingmc.common.context.HierarchicalContext

/**
 * An superinterface responsible for loading [HierarchicalContext]s
 *
 * @since 0.0.5
 * @author kingsthere
 */
interface HierarchicalContextInitializer : ContextInitializer {
    override val context: HierarchicalContext

    /**
     * Add a parent context to initializing context
     */
    fun addParent(context: Context)

}