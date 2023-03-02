package kingmc.common.environment.maven

/**
 * The scope of a dependency
 *
 * @author kingsthere
 * @since 0.0.6
 */
enum class DependencyScope {
    /**
     * The dependency is needed when the code is being compiled, so it will be
     * downloaded while resolving dependencies at runtime
     */
    COMPILE,

    /**
     * The dependency is provided by the runtime environment, so it does not
     * need to be downloaded while resolving dependencies at runtime
     */
    PROVIDED,

    /**
     * The dependency is needed when the application is running, so it will be
     * downloaded while resolving dependencies at runtime
     */
    RUNTIME,

    /**
     * The dependency is needed for compiling and running the unit tests, so it
     * does not need to be downloaded while resolving dependencies at runtime
     */
    TEST,

    /**
     * The dependency should be on the system already, so it does not need to be
     * downloaded while resolving dependencies at runtime
     */
    SYSTEM,

    /**
     * The dependency is actually just a pom and not a jar, so we do not need to
     * download it at all
     */
    IMPORT
}