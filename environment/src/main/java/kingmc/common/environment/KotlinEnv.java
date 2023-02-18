package kingmc.common.environment;

@RuntimeDependency(
        value = "org.jetbrains.kotlin:kotlin-reflect:1.7.21"
)
@RuntimeDependency(
        value = "!org.jetbrains.kotlin:kotlin-stdlib:1.7.21",
        test = "!kotlin.KotlinVersion",
        initiative = true
)
@RuntimeDependency(
        value = "!org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.21",
        test = "!kotlin.jdk7.AutoCloseableKt",
        initiative = true
)
@RuntimeDependency(
        value = "!org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.21",
        test = "!kotlin.collections.jdk8.CollectionsJDK8Kt",
        initiative = true
)
public class KotlinEnv { }