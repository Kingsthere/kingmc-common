package kingmc.util.format

/**
 * An abstract `Formatter` implementation
 *
 * @since 0.0.9
 * @author kingsthere
 */
abstract class AbstractFormatter(val startChar: Char, val endChar: Char) : Formatter {
    override fun format(string: String, formatContext: FormatContext): String {
        return FormatReader(string, startChar, endChar, formatContext).format()
    }

    open class FormatReader(private val string: String, val startChar: Char, val endChar: Char, val formatContext: FormatContext) {
        private var _stringFormatting = string

        fun format(): String {
            val scannedBuilder: MutableList<MutableList<FormatGroupBuilder>> = ArrayList()
            var nest = 0
            var found = 0
            string.forEachIndexed { index, char ->
                if (char == startChar) {
                    if (nest == 0) {
                        scannedBuilder.add(found, ArrayList())
                        found++
                    }
                    nest++
                }
                if (char == endChar) {
                    nest--
                }
                if (nest != 0) {
                    val value = scannedBuilder[found - 1]
                    if (value.size < nest) {
                        value.add(nest - 1,
                            element = FormatGroupBuilder(
                                StringBuilder(),
                                index,
                                index + 1
                            ))
                    }
                    val builders = value.filterIndexed { valueIndex, _ -> valueIndex < nest }
                    builders.forEach {
                        it.end++
                        it.stringBuilder.append(char)
                    }
                }
            }
            val scanned: List<List<FormatGroup>> = ArrayList<List<FormatGroup>>().apply {
                scannedBuilder.forEach { builder ->
                    add(builder.map { FormatGroup(it.stringBuilder.toString(), it.start..it.end) })
                }
            }

            scanned.forEach { formatGroups ->
                val reversed = formatGroups.asReversed()
                reversed.forEachIndexed { index, formatGroup ->
                    val formatArgument = formatContext.getOrNull(formatGroup.string.substring(1)) ?: return@forEachIndexed
                    val key = formatGroup.string + endChar
                    val value = formatArgument.value.toString()
                    _stringFormatting = _stringFormatting.replace(key, value)
                    reversed.filterIndexed { elementIndex, _ -> elementIndex > index }.forEach {
                        it.string = it.string.replace(key, value)
                    }
                }
            }

            return _stringFormatting
        }

        data class FormatGroup(var string: String, val range: IntRange)

        data class FormatGroupBuilder(val stringBuilder: StringBuilder, val start: Int, var end: Int)
    }
}