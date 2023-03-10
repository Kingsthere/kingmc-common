val kingmc_version: String by project
val secret_username: String by project
val secret_password: String by project

group = "net.kingmc"
version = kingmc_version

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "1.7.20"
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
        api(group = "com.google.inject", name = "guice", version = "5.1.0")
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

    publishing {
        repositories {
            maven {
                name = "SNAPSHOT"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                credentials {
                    username = secret_username
                    password = secret_password
                }
            }
            maven {
                name = "Release"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = secret_username
                    password = secret_password
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
    }

    signing {
        sign(publishing.publications["kingmc"])
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
