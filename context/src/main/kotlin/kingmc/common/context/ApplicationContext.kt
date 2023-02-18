package kingmc.common.context

/**
 * Represent an ioc container that stores all
 * codes(types, classes) in the application
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