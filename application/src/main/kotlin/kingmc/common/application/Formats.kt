package kingmc.common.application

import kingmc.util.format.FormatContext

/**
 * Gets the format context of this application
 *
 * @author kingsthere
 * @since 0.0.7
 */
val Application.formatContext: FormatContext
    get() = (this as FormatCapableApplication).getFormatContext()