import java.io.FileInputStream
import java.util.*

val kingmc_version: String by project
val secret = Properties()

secret.load(FileInputStream("secret.properties"))

group = "net.kingmc"
version = kingmc_version

plugins {
    `maven-publish`
    kotlin("jvm") version "1.7.20"
}

subprojects {
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        val ktil = "0.1"
        api("com.kingsthere.ktil:common:$ktil")
        api("com.kingsthere.ktil:annotation:$ktil")

        // Asm
        implementation("org.ow2.asm:asm:9.4")
        implementation("org.ow2.asm:asm-util:9.4")
        implementation("org.ow2.asm:asm-commons:9.4")
        api("com.esotericsoftware:reflectasm:1.11.9")

        // Cglib
        implementation("cglib:cglib:3.3.0")

        api(group = "net.kyori", name = "adventure-api", version = "4.11.0")
        api(group = "net.kyori", name = "adventure-text-minimessage", version = "4.11.0")
        api(group = "net.kyori", name = "adventure-text-serializer-gson", version = "4.11.0")
        api(group = "net.kyori", name = "adventure-text-serializer-legacy", version = "4.11.0")
        api(group = "com.github.ben-manes.caffeine", name = "caffeine", version = "2.9.1")
        api(group = "net.sf.trove4j", name = "trove4j", version = "3.0.3")
        api(group = "com.google.code.gson", name = "gson", version = "2.9.0")
        api(group = "com.google.inject", name = "guice", version = "4.1.0")
        api(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core-jvm", version = "1.6.4")
        api("commons-io:commons-io:2.11.0")

        val kotlinVersion = "1.7.20"
        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
        implementation(kotlin("reflect"))
        testImplementation("commons-io:commons-io:2.11.0")
        // Junit test
        testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api(project(":application"))
    api(project(":common"))
    api(project(":context"))
    api(project(":coroutine"))
    api(project(":configure"))
    api(project(":environment"))
    api(project(":file"))
    api(project(":logging"))
    api(project(":structure"))
}

publishing {
    repositories {
        maven {
            name = "SNAPSHOT"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            credentials {
                username = secret.getProperty("username")
                password = secret.getProperty("password")
            }
        }
    }

    publications {
        create<MavenPublication>("kingmc") {
            groupId = "net.kingmc"
            artifactId = "common"
            version = version.toString()

            from(components.getByName("java"))
        }

    }
}