package kingmc.common.context

/**
 * An ioc container integration integrated common features for applications
 *
 * @since 0.0.1
 * @author kingsthere
 * @see Context
 */
interface ApplicationContext : Context, ConditionCapableContext, HierarchicalContext, LifecycleContext {
    /**
     * The name of this application context
     */
    val name: String
}