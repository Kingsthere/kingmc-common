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
import kotlin.reflect.KClass

/**
 * The dependency dispatcher of current environment
 */
lateinit var dependencyDispatcher: DependencyDispatcher

/**
 * Load dependencies for this class
 *
 * @since 0.0.6
 * @author kingsthere
 */
@OptIn(ExperimentalEnvironmentApi::class)
fun KClass<*>.loadDependencies(formatContext: FormatContext) {
    val relocations: MutableSet<JarRelocation> = mutableSetOf()
    getAnnotationsWithFormattedProperty<Relocate>(formatContext).forEach {
        relocations.add(JarRelocation(it.pattern, it.relocatedPattern))
    }
    getAnnotationsWithFormattedProperty<MavenDependency>(formatContext).forEach {
        val dependency = Dependency(it.groupId, it.artifactId, it.version, DependencyScope.RUNTIME)
        val repository = Repository(it.repository.url)
        dependencyDispatcher.installDependency(dependency, setOf(repository), relocations, *it.scopes)
    }
}

/**
 * Load dependencies for this class suspend
 *
 * @since 0.0.6
 * @author kingsthere
 */
@OptIn(ExperimentalEnvironmentApi::class)
suspend fun KClass<*>.loadDependenciesSuspend(formatContext: FormatContext) = coroutineScope {
    val relocations: MutableSet<JarRelocation> = mutableSetOf()
    getAnnotationsWithFormattedProperty<Relocate>(formatContext).forEach {
        relocations.add(JarRelocation(it.pattern, it.relocatedPattern))
    }
    getAnnotationsWithFormattedProperty<MavenDependency>(formatContext).forEach {
        val dependency = Dependency(it.groupId, it.artifactId, it.version, DependencyScope.RUNTIME)
        val repository = Repository(it.repository.url)
        launch {
            dependencyDispatcher.installDependency(dependency, setOf(repository), relocations, *it.scopes)
        }
    }
}