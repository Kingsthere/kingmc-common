package kingmc.common.structure.annotation

/**
 * An annotation to configure the package need to
 * scan in an [kingmc.common.structure.Project]
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS)
annotation class PackageScan(
    /**
     * The excluded packages to this application
     *
     * @since 0.0.1
     */
    val excluded : Array<String>,

    /**
     * The included packages to this application
     *
     * @since 0.0.1
     */
    val included : Array<String>
)
