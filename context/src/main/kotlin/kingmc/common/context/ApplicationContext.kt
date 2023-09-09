package kingmc.common.context

import kingmc.common.context.process.ProcessorContext

/**
 * An ioc container integration integrated common features for applications
 *
 * @author kingsthere
 * @since 0.0.1
 * @see Context
 */
interface ApplicationContext : Context, ProcessorContext, HierarchicalContext, LifecycleContext {
    /**
     * The name of this application context
     */
    val name: String
}