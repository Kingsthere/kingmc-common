package kingmc.common.context.beans

/**
 * An enum determining the scope of a bean
 *
 * @author kingsthere
 * @since 0.0.2
 */
enum class BeanScope {
    /**
     * Prototype bean
     */
    PROTOTYPE,

    /**
     * Shared bean scope, similar to [SINGLETON] but
     * the bean singleton shared with child contexts
     *
     * @since 0.0.5
     */
    SHARED,

    /**
     * Global bean scope, this bean may only instantiate once
     * in one jvm
     *
     * @since 0.0.5
     */
    GLOBAL,

    /**
     * Singleton bean, a singleton bean may only instantiate once
     * in each context
     */
    SINGLETON
}