import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kingmc_version: String by project
val ossrhUsername: String by project
val ossrhPassword: String by project

group = "net.kingmc"
version = kingmc_version

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "1.8.10"
}

allprojects {
    apply(plugin = "org.gradle.signing")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        // ASM
        implementation("org.ow2.asm:asm:9.4")
        implementation("org.ow2.asm:asm-util:9.4")
        implementation("org.ow2.asm:asm-commons:9.4")
        implementation("io.github.classgraph:classgraph:4.8.158")
        api("com.esotericsoftware:reflectasm:1.11.9")

        // Cglib
        implementation("cglib:cglib:3.3.0")

        api(group = "net.kyori", name = "adventure-api", version = "4.11.0")
        api(group = "net.kyori", name = "adventure-text-minimessage", version = "4.11.0")
        api(group = "net.kyori", name = "adventure-text-serializer-gson", version = "4.11.0")
        api(group = "net.kyori", name = "adventure-text-serializer-legacy", version = "4.11.0")
        api(group = "com.google.code.gson", name = "gson", version = "2.9.0")
        api(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core-jvm", version = "1.6.4")
        api("org.yaml:snakeyaml:2.0")
        api("com.github.ben-manes.caffeine:caffeine:2.9.1")
        api("com.google.guava:guava:31.1-jre")
        api("it.unimi.dsi:fastutil:8.5.12")
        api("commons-io:commons-io:2.11.0")

        val kotlinVersion = "1.8.10"
        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
        implementation(kotlin("reflect"))
        testImplementation("commons-io:commons-io:2.11.0")
        // Junit test
        testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    }

    publishing {
        repositories {
            mavenLocal()
            maven {
                name = "Snapshot"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
            maven {
                name = "Release"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }

        publications {
            create<MavenPublication>("kingmc") {
//                groupId = "net.kingmc"
//                artifactId = "common"
//                version = version.toString()

                pom {
                    name.set("KingMC Common")
                    packaging = "jar"
                    description.set("A high performance minecraft plugin framework")
                    url.set("https://www.kingmc.net")

                    scm {
                        url.set("https://github.com/Kingsthere/kingmc-common.git")
                    }

                    licenses {
                        license {
                            name.set("The MIT License")
                            url.set("https://mit-license.org/")
                        }
                    }

                    developers {
                        developer {
                            id.set("kingsthere")
                            name.set("Kingsthere")
                            email.set("kingsthere0@hotmail.com")
                        }
                    }
                }

                from(components.getByName("java"))
            }

        }
    }

    java {
        withSourcesJar()
        withJavadocJar()
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    signing {
        sign(publishing.publications["kingmc"])
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            // useK2 = true
        }
    }

    tasks.withType<Javadoc> {
        options {
            encoding = "UTF-8"
            isFailOnError = false
            quiet()
        }
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
