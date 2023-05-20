package kingmc.util.format

import java.util.*

/**
 * Extended [FormatContext], it obtain format arguments from a [Properties]
 *
 * @since 0.0.6
 * @author kingsthere
 */
class PropertiesFormatContext(val properties: Properties): ListFormatArguments(buildList {
    var index = 0
    properties.forEach { key, value ->
        add(FormatArgument(value, key.toString()))
        index++
    }
})