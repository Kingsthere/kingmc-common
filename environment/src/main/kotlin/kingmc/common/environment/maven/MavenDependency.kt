package kingmc.common.environment.maven

import kingmc.util.format.Formatted

/**
 * Indicating a maven dependency needed to run this class
 *
 * @since 0.0.6
 * @author kingsthere
 */
@Target(AnnotationTarget.CLASS)
@Retention
@Repeatable
@MustBeDocumented
annotation class MavenDependency(
    /**
     * The group id of this maven dependency
     */
    @Formatted
    val groupId: String,

    /**
     * The artifact id of this maven dependency
     */
    @Formatted
    val artifactId: String,

    /**
     * The version of this maven dependency
     * `RELEASE` for the release version
     * `LATEST` for the latest version
     */
    @Formatted
    val version: String = "RELEASE",

    /**
     * The specifies repository of this maven dependency
     */
    val repository: MavenRepository = MavenRepository("{ kingmc.environment.maven-repository }"),

    /**
     * The scope of this maven dependency, only dependencies scopped [scopes] can apply
     */
    val scopes: Array<DependencyScope> = [DependencyScope.RUNTIME]
)
