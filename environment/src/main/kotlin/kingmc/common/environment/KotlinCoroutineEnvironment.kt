package kingmc.common.environment

import kingmc.common.environment.maven.MavenDependency

@MavenDependency(
    groupId = "org.jetbrains.kotlinx",
    artifactId = "kotlinx-coroutines-core-jvm",
    version = "{ kingmc.environment.kotlinx-coroutine }"
)
object KotlinCoroutineEnvironment