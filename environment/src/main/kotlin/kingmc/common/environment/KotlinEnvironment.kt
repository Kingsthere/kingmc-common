package kingmc.common.environment

import kingmc.common.environment.maven.MavenDependency

@MavenDependency(
    groupId = "org.jetbrains.kotlinx",
    artifactId = "kotlinx-coroutines-core",
    version = "{ kingmc.environment.kotlinx-coroutine }"
)
@MavenDependency(
    groupId = "org.jetbrains.kotlin",
    artifactId = "kotlin-reflect",
    version = "{ kingmc.environment.kotlin }"
)
object KotlinEnvironment