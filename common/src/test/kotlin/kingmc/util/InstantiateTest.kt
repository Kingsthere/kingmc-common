package kingmc.util

import org.junit.jupiter.api.Test

class InstantiateTest {
    @Test
    fun testSingletonMap() {
        println(ObjectA::class.instance(SingletonMap()))
        println(ObjectA::class.instance(SingletonMap()))
    }
}

class ObjectA