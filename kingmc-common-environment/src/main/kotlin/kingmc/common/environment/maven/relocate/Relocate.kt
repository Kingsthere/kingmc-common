package kingmc.common.environment.maven.relocate

import kingmc.common.environment.ExperimentalEnvironmentApi
import kingmc.util.format.Formatted

/**
 * Annotation to declare a relocation of a maven dependency
 *
 * @author kingsthere
 * @since 0.0.6
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
     * The relocated pattern after relocating
     */
    @Formatted
    val relocatedPattern: String
)
