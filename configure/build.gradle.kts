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
    val nightConfig = "3.6.5"
    // Night config
    // Effective config framework in java
    api(group = "com.electronwill.night-config", name = "core", version = nightConfig)
    // Night config toml addon
    api(group = "com.electronwill.night-config", name = "toml", version = nightConfig)
    // Night config yaml addon
    api(group = "com.electronwill.night-config", name = "yaml", version = nightConfig)
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", "src/main/java")
    }
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("configure") {
            groupId = group.toString()
            artifactId = project.name
            version = version.toString()

            from(components.getByName("java"))
        }

    }
}