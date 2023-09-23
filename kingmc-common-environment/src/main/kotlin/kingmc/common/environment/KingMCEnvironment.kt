package kingmc.common.environment

import kingmc.common.environment.maven.MavenDependency

@MavenDependency(
    groupId = "com.esotericsoftware",
    artifactId = "reflectasm",
    version = "1.11.9"
)
@MavenDependency(
    groupId = "net.kyori",
    artifactId = "adventure-api",
    version = "4.11.0"
)
@MavenDependency(
    groupId = "net.kyori",
    artifactId = "adventure-text-minimessage",
    version = "4.11.0"
)
@MavenDependency(
    groupId = "net.kyori",
    artifactId = "adventure-text-serializer-gson",
    version = "4.11.0"
)
@MavenDependency(
    groupId = "net.kyori",
    artifactId = "adventure-text-serializer-legacy",
    version = "4.11.0"
)
@MavenDependency(
    groupId = "com.google.code.gson",
    artifactId = "gson",
    version = "2.9.0"
)
@MavenDependency(
    groupId = "io.github.classgraph",
    artifactId = "classgraph",
    version = "4.8.158"
)
@MavenDependency(
    groupId = "net.sf.trove4j",
    artifactId = "trove4j",
    version = "3.0.3"
)
@MavenDependency(
    groupId = "org.jetbrains",
    artifactId = "annotations",
    version = "23.0.0",
)
object KingMCEnvironment