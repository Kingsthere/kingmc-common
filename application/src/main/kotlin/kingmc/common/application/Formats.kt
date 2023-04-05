package kingmc.common.application

import kingmc.util.format.FormatContext

/**
 * The format context of this application
 *
 * @since 0.0.7
 * @author kingsthere
 */
val Application.formatContext: FormatContext
    get() = (this as FormatCapableApplication).getFormatContext()