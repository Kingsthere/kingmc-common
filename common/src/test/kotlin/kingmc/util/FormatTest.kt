package kingmc.util

import kingmc.util.format.FormatArgument
import kingmc.util.format.ListFormatArguments
import kingmc.util.format.formatWithContext
import org.junit.jupiter.api.Test

class FormatTest {
    @Test
    fun format() {
        val context = ListFormatArguments(listOf(FormatArgument(1, "Notch", "player"), FormatArgument(1, "Kingsthere", "player2")))
        assert(context.formatWithContext("Hello { player }, and { player2 }!") == "Hello Notch, and Kingsthere!")
    }
}