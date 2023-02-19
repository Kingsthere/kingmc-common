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

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", "src/main/java")
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}