package kingmc.common.environment.maven.model

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
