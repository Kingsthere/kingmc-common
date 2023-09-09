package kingmc.util

import kingmc.util.lifecycle.Execution
import kingmc.util.lifecycle.Lifecycle
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LifecycleTest {
    @Test
    fun testLifecycle() {
        val lifecycle = Lifecycle()
        var passed1 = false
        var passed2 = false
        var passed3 = false
        var passed4 = false
        var passed5 = false
        lifecycle.scheduleExecution(0, Execution(1, "test1") {
            passed1 = true
        })
        lifecycle.scheduleExecution(0, Execution(0, "test2") {
            Assertions.assertTrue(passed1) { "test1 with priority 1 should be executed first" }
            passed2 = true
        })
        lifecycle.scheduleExecution(1, Execution(0, "test3") {
            Assertions.assertTrue(passed2) { "test2 with lifecycle 1 should be executed first" }
            Assertions.assertTrue(passed4) { "test4 with priority 2 should be executed first" }
            passed3 = true
        })
        lifecycle.scheduleExecution(1, Execution(2, "test4") {
            Assertions.assertTrue(passed3) { "test2 with lifecycle 1 should be executed first" }
            passed4 = true
        })
        lifecycle.scheduleExecution(1, Execution(1, "test5") {
            Assertions.assertTrue(passed5) { "test6 with same priority but inserted late than test5 should be executed first" }
        })
        lifecycle.scheduleExecution(1, Execution(1, "test6") {
            passed5 = true
        })
    }
}