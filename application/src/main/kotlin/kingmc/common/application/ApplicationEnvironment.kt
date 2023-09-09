package kingmc.common.application

/**
 * Represents an environment in which [Application] runs
 *
 * @author kingsthere
 * @since 0.0.2
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