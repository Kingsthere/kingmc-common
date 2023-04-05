package kingmc.common.context.format

import kingmc.common.context.Context
import kingmc.util.format.ListFormatArguments

/**
 * A format context for a [Context]
 *
 * @since 0.0.7
 * @author kingsthere
 */
class ContextFormatContext(context: Context) : ListFormatArguments(context.formatContexts.flatMap { it.toList() })