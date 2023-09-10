dependencies {
    implementation(project(":${rootProject.name}-api"))
    implementation(project(":${rootProject.name}-logging"))
    implementation(project(":${rootProject.name}-application"))
    implementation(project(":${rootProject.name}-file"))

    // Jar Relocator
    api("me.lucko:jar-relocator:1.5")
}