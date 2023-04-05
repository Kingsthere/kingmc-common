package kingmc.common.environment.maven.relocate

import kingmc.common.environment.ExperimentalEnvironmentApi
import kingmc.util.format.Formatted

/**
 * Annotation to declare a relocation of a maven dependency
 *
 * @since 0.0.6
 * @author kingsthere
 */
@Target(AnnotationTarget.CLASS)
@Retention
@Repeatable
@ExperimentalEnvironmentApi
@MustBeDocumented
annotation class Relocate(
    /**
     * The pattern to relocate
     */
    @Formatted
    val pattern: String,

    /**
     * The relocated pattern after relocate
     */
    @Formatted
    val relocatedPattern: String
)
