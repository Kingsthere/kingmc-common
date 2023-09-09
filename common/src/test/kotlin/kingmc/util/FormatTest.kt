package kingmc.util

import kingmc.util.format.FormatArgument
import kingmc.util.format.FormatContext
import kingmc.util.format.formatWithContext
import org.junit.jupiter.api.Test

class FormatTest {
    @Test
    fun format() {
        val context = FormatContext(
            listOf(
                FormatArgument("Notch", "player"),
                FormatArgument("2", "index"),
                FormatArgument("Kingsthere", "player2")
            )
        )
        assert(context.formatWithContext("Hello {player}, and {player{index}}!") == "Hello Notch, and Kingsthere!")
    }
}