package kingmc.common.environment.maven.model

/**
 * A package class packaged [dependencies] and [repositories] for an environment
 *
 * @author kingsthere
 * @since 0.0.6
 */
data class Pom(val dependencies: List<Dependency>, val repositories: List<Repository>)