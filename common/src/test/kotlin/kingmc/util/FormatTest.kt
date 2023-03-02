package kingmc.util

import kingmc.util.format.ListFormatArguments
import kingmc.util.format.format
import kingmc.util.format.FormatArgument
import org.junit.jupiter.api.Test

class FormatTest {
    @Test
    fun testFormat() {
        val context = ListFormatArguments(listOf(FormatArgument(1, "Notch", "player"), FormatArgument(1, "Kingsthere", "player2")))
        assert(context.format("Hello { player }, and { player2 }!") == "Hello Notch, and Kingsthere!")
    }
}