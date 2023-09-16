rootProject.name = "kingmc-common"

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

val prefix: String = rootProject.name
include(":$prefix-api")
include(":$prefix-file")
include(":$prefix-configure")
include(":$prefix-context")
include(":$prefix-logging")
include(":$prefix-coroutine")
include(":$prefix-application")
include(":$prefix-environment")
