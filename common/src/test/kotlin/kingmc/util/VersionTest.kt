package kingmc.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VersionTest {

    @Test
    fun compareTo() {
        val version1 = Version("1.0.1")
        val version3 = Version("1.0.0")
        val version2 = Version("1.0.6")
        Assertions.assertTrue(version2 > version1)
        Assertions.assertTrue(version1 < version2)

        // 1.0.1 -> Latest
        val toLatest = version1..Version.LATEST

        Assertions.assertTrue(version1 in toLatest)
        Assertions.assertFalse(version3 in toLatest)
    }
}