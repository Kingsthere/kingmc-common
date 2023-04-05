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
     *
     * You can add `!` sign to dots to prevent shadow plugins relocate the groupId
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
     * The classifier of this dependency
     */
    val classifier: String = "",

    /**
     * The url of specifies repository of this maven dependency
     */
    val repository: String = "{ kingmc.environment.maven-repository }",

    /**
     * The scope of this maven dependency, only dependencies scopped [scope] can apply
     */
    val scope: DependencyScope = DependencyScope.RUNTIME
)
