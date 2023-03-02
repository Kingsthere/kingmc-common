package kingmc.common.environment.maven

import kingmc.util.format.Formatted

/**
 * Annotation to declare a maven repository for downloading [maven dependencies][MavenDependency]
 *
 * @since 0.0.6
 * @author kingsthere
 */
@Retention
@Target()
annotation class MavenRepository(
    /**
     * The url of this maven repository
     */
    @Formatted
    val url: String
)
