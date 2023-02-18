val kingmc_version: String by project

group = "net.kingmc.common"
version = kingmc_version

plugins {
    id("java")
    `maven-publish`
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
