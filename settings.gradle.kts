
rootProject.name = "common"

pluginManagement {
    plugins {
        id("net.kingmc.gradle-plugin") version "0.0.4"
        kotlin("jvm") version "1.9.0"
    }
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

include("common")
include("file")
include("configure")
include("context")
include("logging")
include("coroutine")
include("application")
include("environment")
