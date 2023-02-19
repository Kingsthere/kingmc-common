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
    implementation(project(":context"))
    implementation(project(":application"))
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", "src/main/java")
    }
}
