rootProject.name = "kingmc-common"

pluginManagement {
    plugins {
        id("net.kingmc.gradle-plugin") version "0.0.4"
    }
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

val prefix = rootProject.name
include(":$prefix-api")
include(":$prefix-file")
include(":$prefix-configure")
include(":$prefix-context")
include(":$prefix-logging")
include(":$prefix-coroutine")
include(":$prefix-application")
include(":$prefix-environment")
