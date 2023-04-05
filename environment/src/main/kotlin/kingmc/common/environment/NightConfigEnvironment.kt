package kingmc.common.environment

import kingmc.common.environment.maven.MavenDependency

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
@MavenDependency(
    groupId = "com.electronwill.night-config",
    artifactId = "hocon",
    version = "3.6.5"
)
object NightConfigEnvironment