package kingmc.util

import org.junit.jupiter.api.Test

class InstantiateTest {
    @Test
    fun singletonMap() {
        val singletonMap = SingletonMap()
        val testReference1 = ObjectA::class.instance(singletonMap)
        val testReference2 = ObjectA::class.instance(singletonMap)
        assert(testReference1 == testReference2)
    }

    @Test
    fun prototypeMap() {
        val testReference1 = ObjectA::class.instance(PrototypeMap)
        val testReference2 = ObjectA::class.instance(PrototypeMap)
        assert(testReference1 != testReference2)
    }

    class ObjectA
}