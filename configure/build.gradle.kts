val kingmc_version: String by project

group = "net.kingmc.common"
version = kingmc_version

plugins {
    id("java")
    `maven-publish`
}

java {
    withSourcesJar()
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))


    // The version of night config
    val nightConfig = "3.6.6"
    // Night config
    api(group = "com.electronwill.night-config", name = "core", version = nightConfig)
    api(group = "com.electronwill.night-config", name = "toml", version = nightConfig)
    api(group = "com.electronwill.night-config", name = "yaml", version = nightConfig)
    api(group = "com.electronwill.night-config", name = "hocon", version = nightConfig)
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", "src/main/java")
    }
}
