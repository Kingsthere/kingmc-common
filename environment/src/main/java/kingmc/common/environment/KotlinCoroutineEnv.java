package kingmc.common.environment;

@RuntimeDependency(
        value = "!org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4",
        test = "!kotlinx.coroutines.Job",
        initiative = true
)
public class KotlinCoroutineEnv { }