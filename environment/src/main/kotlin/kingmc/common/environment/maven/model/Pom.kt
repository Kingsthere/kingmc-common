package kingmc.common.environment.maven.model

/**
 * A package class packaged [dependencies] and [repositories] for an environment
 *
 * @since 0.0.6
 * @author kingsthere
 */
data class Pom(val dependencies: List<Dependency>, val repositories: List<Repository>)