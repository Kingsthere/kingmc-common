package kingmc.common.environment

import kingmc.common.environment.maven.DependencyScope
import kingmc.common.environment.maven.model.Dependency
import kingmc.common.environment.maven.model.JarRelocation
import kingmc.common.environment.maven.model.Repository

/**
 * Data class represents a dependency declared by `MavenDependency` annotation
 *
 * @author kingsthere
 * @since 0.1.2
 */
data class DependencyDeclaration(
    val dependency: Dependency,
    val repository: Repository,
    val relocations: Collection<JarRelocation> = emptyList(),
    val scopes: Array<out DependencyScope> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DependencyDeclaration

        if (dependency != other.dependency) return false
        if (repository != other.repository) return false
        if (relocations != other.relocations) return false
        if (!scopes.contentEquals(other.scopes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dependency.hashCode()
        result = 31 * result + repository.hashCode()
        result = 31 * result + relocations.hashCode()
        result = 31 * result + scopes.contentHashCode()
        return result
    }
}