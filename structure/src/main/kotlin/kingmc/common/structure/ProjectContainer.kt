package kingmc.common.structure

/**
 * Represent a container to stores [Project] that is loaded
 *
 * @see Project
 * @since 0.0.1
 * @author kingsthere
 */
interface ProjectContainer {
    /**
     * The central project container instance that
     * commonly used ([Projects])
     *
     * @since 0.0.1
     * @author kingsthere
     */
    val central: ProjectContainer
        get() = Projects

    /**
     * Get all projects in this container
     * as a [Set]
     *
     * @see Project
     * @see Set
     * @since 0.0.1
     */
    fun getProjects(): Set<Project>

    /**
     * Get all pluggable in each [Project] that
     * is in this container as a [Set]
     *
     * @since 0.0.1
     * @see Project
     * @see Pluggable
     * @see Set
     */
    @ExperimentalStructureApi
    fun getPluggable(): Set<Pluggable> {
        // Create the instance of returning set
        val value: Set<Pluggable> = HashSet()
        // Add all pluggable in each projects
        // into the returning set
        for (project in getProjects()) {
            value.plus(project.getPluggable())
        }
        // Return the set
        return value
    }
}