object Versions {
    const val ADVENTURE = "4.11.0"
    const val KOTLINX_COROUTINE = "1.7.3"
    const val ERRORPRONE = "2.11.0"
    const val GSON = "2.9.0"
    const val TROVE = "3.0.3"
    const val COMMONS_IO = "2.11.0"
    const val CGLIB = "3.3.0"
    const val CLASSGRAPH = "4.8.162"
    const val REFLECTASM = "1.11.9"
}

object Libs {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$KOTLIN_VERSION"
    const val KOTLINX_COROUTINE_CORE_JVM =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Versions.KOTLINX_COROUTINE}"
    const val ADVENTURE_API = "net.kyori:adventure-api:${Versions.ADVENTURE}"
    const val ADVENTURE_TEXT_MINIMESSAGE = "net.kyori:adventure-text-minimessage:${Versions.ADVENTURE}"
    const val ADVENTURE_TEXT_SERIALIZER_GSON = "net.kyori:adventure-text-serializer-gson:${Versions.ADVENTURE}"
    const val ADVENTURE_TEXT_SERIALIZER_LEGACY = "net.kyori:adventure-text-serializer-legacy:${Versions.ADVENTURE}"
    const val ERRORPRONE = "com.google.errorprone:error_prone_annotations:${Versions.ERRORPRONE}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val TROVE4J = "net.sf.trove4j:trove4j:${Versions.TROVE}"
    const val COMMONS_IO = "commons-io:commons-io:${Versions.COMMONS_IO}"
    const val CGLIB = "cglib:cglib:${Versions.CGLIB}"
    const val CLASSGRAPH = "io.github.classgraph:classgraph:${Versions.CLASSGRAPH}"
    const val REFLECTASM = "com.esotericsoftware:reflectasm:${Versions.REFLECTASM}"
}