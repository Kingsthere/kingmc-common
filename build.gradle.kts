import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ossrhUsername: String by project
val ossrhPassword: String by project

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version KOTLIN_VERSION
}

allprojects {
    group = "net.kingmc"
    version = KINGMC_VERSION

    apply(plugin = "java")
    apply(plugin = "org.gradle.signing")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        api(Libs.CLASSGRAPH)
        api(Libs.REFLECTASM)
        implementation(Libs.CGLIB)

        api(Libs.ADVENTURE_API)
        api(Libs.ADVENTURE_TEXT_MINIMESSAGE)
        api(Libs.ADVENTURE_TEXT_SERIALIZER_GSON)
        api(Libs.ADVENTURE_TEXT_SERIALIZER_LEGACY)
        api(Libs.GSON)
        api(Libs.KOTLIN_STDLIB)
        api(Libs.KOTLIN_REFLECT)
        api(Libs.KOTLINX_COROUTINE_CORE_JVM)
        api(Libs.COMMONS_IO)
        api(Libs.TROVE4J)
        api(Libs.ERRORPRONE)

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

    sourceSets {
        main {
            java.srcDirs("src/main/kotlin", "src/main/java")
        }
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

dependencies {
    api(project(":${rootProject.name}-api"))
    api(project(":${rootProject.name}-application"))
    api(project(":${rootProject.name}-context"))
    api(project(":${rootProject.name}-coroutine"))
    api(project(":${rootProject.name}-configure"))
    api(project(":${rootProject.name}-environment"))
    api(project(":${rootProject.name}-file"))
    api(project(":${rootProject.name}-logging"))
}

extra["kotlin.code.style"] = "official"