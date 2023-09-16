package kingmc.util.text

import kingmc.common.text.*
import net.kyori.adventure.text.format.TextDecoration

/**
 * Create a stack trace text for the receiver throwable
 *
 * @author kingsthere
 * @since 0.1.3
 */
fun Throwable.stackTraceToText(): Text =
    Text {
        append(toText().color(RED))
        append(Text("\n"))
        for (stackTraceElement in stackTrace) {
            append(Text("  at ").color(DARK_GRAY))
            val stackTraceElementStrings = stackTraceElement.toString().split("(")
            val first = stackTraceElementStrings[0]
            val second = stackTraceElementStrings[1]
            append(Text(first).color(RED))
            append(
                Text("($second")
                .style(TextStyle {
                    color(BLUE)
                    decorate(TextDecoration.UNDERLINED)
                })
            )
            append(Text("\n"))
        }
        cause?.let { cause ->
            append(Text("Caused by: ").color(RED))
            append(cause.stackTraceToText())
        }
    }