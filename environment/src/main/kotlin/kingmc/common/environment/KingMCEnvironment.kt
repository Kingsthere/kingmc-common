package kingmc.common.environment

import kingmc.common.environment.maven.MavenDependency

@MavenDependency(
    groupId = "cglib",
    artifactId = "cglib",
    version = "3.2.2"
)
@MavenDependency(
    groupId = "com.esotericsoftware",
    artifactId = "reflectasm",
    version = "1.11.9"
)
@MavenDependency(
    groupId = "com.github.ben-manes.caffeine",
    artifactId = "caffeine",
    version = "2.9.1"
)
@MavenDependency(
    groupId = "net.sf.trove4j",
    artifactId = "trove4j",
    version = "3.0.3"
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
    groupId = "com.electronwill.night-config",
    artifactId = "core",
    version = "3.6.5"
)
@MavenDependency(
    groupId = "com.electronwill.night-config",
    artifactId = "yaml",
    version = "3.6.5"
)
@MavenDependency(
    groupId = "com.electronwill.night-config",
    artifactId = "toml",
    version = "3.6.5"
)
object KingMCEnvironment