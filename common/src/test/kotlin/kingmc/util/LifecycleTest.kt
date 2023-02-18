package kingmc.util

import org.junit.jupiter.api.Test

class LifecycleTest {
    @Test
    fun testLifecycle() {
        val lifecycle = lifecycle<Runnable>()
    }
}