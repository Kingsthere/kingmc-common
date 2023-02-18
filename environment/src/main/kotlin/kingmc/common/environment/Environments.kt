package kingmc.common.environment

import kingmc.util.KingMCDsl
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.IOException
import kotlin.reflect.KClass

/**
 * Create and return a downloadable maven dependency
 *
 * @since 0.0.3
 * @author kingsthere
 */
fun dependency(groupId: String, artifactId: String, version: String, scope: DependencyScope = DependencyScope.COMPILE): Dependency =
    Dependency(groupId, artifactId, version, scope)

/**
 * Create and return a repository
 *
 * @since 0.0.3
 * @author kingsthere
 */
fun repository(url: String = "https://repo.maven.apache.org/maven2"): Repository =
    Repository(url)

suspend fun RuntimeEnv.loadDependencySuspend(clazz: Class<*>, initiative: Boolean) {
    val baseFile = File(RuntimeEnv.defaultLibrary)
    var dependencies: Array<RuntimeDependency>? = null
    if (clazz.isAnnotationPresent(RuntimeDependency::class.java)) {
        dependencies = clazz.getAnnotationsByType(RuntimeDependency::class.java)
    } else {
        val annotation = clazz.getAnnotation(RuntimeDependencies::class.java)
        if (annotation != null) {
            dependencies = annotation.value
        }
    }
    if (dependencies != null) {
        for (dependency in dependencies) {
            if (dependency.initiative && !initiative) {
                continue
            }
            val allTest: String = dependency.test
            val tests: MutableList<String> = ArrayList()
            if (allTest.contains(",")) {
                tests.addAll(listOf(*allTest.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()))
            } else {
                tests.add(allTest)
            }
            if (tests.stream().allMatch { path: String? ->
                    test(
                        path
                    )
                }) {
                continue
            }
            coroutineScope {
                val relocation: MutableList<JarRelocation> = ArrayList()
                val relocate: Array<String> = dependency.relocate.map { it.replace("!.", ".") }.toTypedArray()
                require(relocate.size % 2 == 0) { "unformatted relocate" }
                var i = 0
                while (i + 1 < relocate.size) {
                    val pattern = relocate[i]
                    val relocatePattern =
                        relocate[i + 1]
                    relocation.add(JarRelocation(pattern, relocatePattern))
                    i += 2
                }
                try {
                    val url: String =
                        dependency.value.replace("!.", ".")
                    loadDependency(
                        url,
                        baseFile,
                        relocation,
                        dependency.repository,
                        dependency.ignoreOptional,
                        dependency.ignoreException,
                        dependency.transitive,
                        dependency.scopes
                    )
                } catch (ex: IOException) {
                    ex.printStackTrace()
                } catch (e: RuntimeException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

/**
 * Load all dependencies that is declared in current
 * class
 *
 * @since 0.0.3
 * @author kingsthere
 */
fun KClass<*>.loadDependencies() {
    RuntimeEnv.ENV.loadDependency(this.java, true)
}

/**
 * Load all dependencies that is declared in current class
 */
@KingMCDsl
fun Any.loadDependencies() {
    this::class.loadDependencies()
}

/**
 * Load all dependencies that is declared in current
 * class
 *
 * @since 0.0.3
 * @author kingsthere
 */
suspend fun KClass<*>.loadDependenciesSuspend() {
    RuntimeEnv.ENV.loadDependencySuspend(this.java, true)
}