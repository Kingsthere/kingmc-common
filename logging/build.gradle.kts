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
    implementation(project(":application"))
    implementation(project(":context"))
    // Slf4j logging
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    api("org.slf4j:slf4j-api:2.0.3")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-reload4j
    api("org.slf4j:slf4j-reload4j:2.0.3")
    // https://mvnrepository.com/artifact/net.kyori/adventure-text-logger-slf4j
    api("net.kyori:adventure-text-logger-slf4j:4.12.0")
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
        create<MavenPublication>("logging") {
            groupId = group.toString()
            artifactId = project.name
            version = version.toString()

            from(components.getByName("java"))
        }

    }
}
