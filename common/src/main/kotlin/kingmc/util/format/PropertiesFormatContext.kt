package kingmc.util.format

import java.util.*

/**
 * Extended [FormatContext], it obtain format arguments from a [Properties]
 *
 * @since 0.0.6
 * @author kingsthere
 */
class PropertiesFormatContext(val properties: Properties): ListFormatArguments() {
    override val arguments: List<FormatArgument<*>>
        get() = properties.map { (key, value) -> FormatArgument(value, key.toString()) }
}