package kingmc.common.environment

import kingmc.common.environment.maven.DependencyDispatcher
import kingmc.common.environment.maven.DependencyScope
import kingmc.common.environment.maven.MavenDependency
import kingmc.common.environment.maven.model.Dependency
import kingmc.common.environment.maven.model.JarRelocation
import kingmc.common.environment.maven.model.Repository
import kingmc.common.environment.maven.relocate.Relocate
import kingmc.util.annotation.getAnnotationsWithFormattedProperty
import kingmc.util.format.FormatContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Future
import kotlin.reflect.KClass

/**
 * Install all the given dependencies for this dependency dispatcher in coroutine
 *
 * @param dependencyDeclarations dependencies to install
 * @author kingsthere
 * @since 0.1.2
 */
suspend fun DependencyDispatcher.installDependenciesSuspend(dependencyDeclarations: Iterable<DependencyDeclaration>) =
    coroutineScope {
        launch {
            dependencyDeclarations.forEach { dependency ->
                installDependency(dependency)
            }
        }
    }

/**
 * Install all the given dependencies for this dependency dispatcher asynchronously
 *
 * @param dependencyDeclarations dependencies to install
 * @author kingsthere
 * @since 0.1.2
 */
fun DependencyDispatcher.installDependenciesAsync(dependencyDeclarations: Iterable<DependencyDeclaration>): List<Future<*>> {
    return dependencyDeclarations.map { installDependencyAsync(it) }
}

/**
 * Load dependencies for this class
 *
 * @author kingsthere
 * @since 0.0.6
 */
@OptIn(ExperimentalEnvironmentApi::class)
fun KClass<*>.loadDependencies(dependencyDispatcher: DependencyDispatcher, formatContext: FormatContext) {
    val relocations: MutableSet<JarRelocation> = mutableSetOf()
    getAnnotationsWithFormattedProperty<Relocate>(formatContext).forEach {
        relocations.add(JarRelocation(it.pattern.replace("!", ""), it.relocatedPattern))
    }
    getAnnotationsWithFormattedProperty<MavenDependency>(formatContext).forEach {
        val dependency = Dependency(it.groupId, it.artifactId, it.version, DependencyScope.RUNTIME)
        val repository = Repository(it.repository)
        dependencyDispatcher.installDependency(dependency, repository, relocations, it.scope)
    }
}

/**
 * Load dependencies for this class asynchronously
 *
 * @author kingsthere
 * @since 0.0.6
 */
@OptIn(ExperimentalEnvironmentApi::class)
fun KClass<*>.loadDependenciesAsync(
    dependencyDispatcher: DependencyDispatcher,
    formatContext: FormatContext
): List<Future<*>> {
    val tasks = mutableListOf<Future<*>>()
    val relocations: MutableSet<JarRelocation> = mutableSetOf()
    getAnnotationsWithFormattedProperty<Relocate>(formatContext).forEach {
        relocations.add(JarRelocation(it.pattern.replace("!", ""), it.relocatedPattern))
    }
    getAnnotationsWithFormattedProperty<MavenDependency>(formatContext).forEach {
        val dependency = Dependency(it.groupId, it.artifactId, it.version, DependencyScope.RUNTIME)
        val repository = Repository(it.repository)
        tasks.add(dependencyDispatcher.installDependencyAsync(dependency, repository, relocations, it.scope))
    }
    return tasks
}

/**
 * Load dependencies for this class in coroutine
 *
 * @author kingsthere
 * @since 0.0.6
 */
@OptIn(ExperimentalEnvironmentApi::class)
suspend fun KClass<*>.loadDependenciesSuspend(
    dependencyDispatcher: DependencyDispatcher,
    formatContext: FormatContext
) = coroutineScope {
    val relocations: MutableSet<JarRelocation> = mutableSetOf()
    getAnnotationsWithFormattedProperty<Relocate>(formatContext).forEach {
        relocations.add(JarRelocation(it.pattern.replace("!", ""), it.relocatedPattern))
    }
    getAnnotationsWithFormattedProperty<MavenDependency>(formatContext).forEach {
        val dependency = Dependency(it.groupId, it.artifactId, it.version, DependencyScope.RUNTIME)
        val repository = Repository(it.repository)
        launch {
            dependencyDispatcher.installDependency(dependency, repository, relocations, it.scope)
        }
    }
}