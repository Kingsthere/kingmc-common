dependencies {
    implementation(project(":${rootProject.name}-api"))
    implementation(project(":${rootProject.name}-application"))
    implementation(project(":${rootProject.name}-context"))
    // Slf4j logging
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    api("org.slf4j:slf4j-api:2.0.3")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-reload4j
    api("org.slf4j:slf4j-reload4j:2.0.3")
    // https://mvnrepository.com/artifact/net.kyori/adventure-text-logger-slf4j
    api("net.kyori:adventure-text-logger-slf4j:4.12.0")
}