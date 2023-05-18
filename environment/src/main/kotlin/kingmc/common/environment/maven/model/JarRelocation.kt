package kingmc.common.environment.maven.model

import kingmc.util.format.FormatContext
import kingmc.util.format.Formatted
import kingmc.util.format.formatWithContext

/**
 * A data class represents a relocation
 *
 * @since 0.0.7
 * @author kingsthere
 */
data class JarRelocation(
    /**
     * The pattern to relocate
     */
    val pattern: String,

    /**
     * The relocated pattern after relocate
     */
    val relocatedPattern: String
)

@Formatted
fun jarRelocation(pattern: String, relocatedPattern: String, formatContext: FormatContext) =
    JarRelocation(formatContext.formatWithContext(pattern), formatContext.formatWithContext(relocatedPattern))