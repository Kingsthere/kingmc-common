package kingmc.common.application

/**
 * Represents an environment in which [Application] runs
 *
 * @since 0.0.2
 * @author kingsthere
 * @see Application
 */
interface ApplicationEnvironment {

    /**
     * [ClassLoader] used to load application.
     *
     * Useful for various reflection-based services, like dependency injection.
     */
    val classLoader: ClassLoader
}