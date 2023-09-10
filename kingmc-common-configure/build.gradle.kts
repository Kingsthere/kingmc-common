dependencies {
    implementation(project(":${rootProject.name}-api"))

    // The version of night config
    val nightConfig = "3.6.6"
    // Night config
    api(group = "com.electronwill.night-config", name = "core", version = nightConfig)
    api(group = "com.electronwill.night-config", name = "toml", version = nightConfig)
    api(group = "com.electronwill.night-config", name = "yaml", version = nightConfig)
    api(group = "com.electronwill.night-config", name = "hocon", version = nightConfig)
}