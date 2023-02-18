package kingmc.common.structure

/**
 * Represent a project that is combined from other
 * projects
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface CombinedProject : Project {
    /**
     * Combine a project to this
     */
    fun combine(project: Project)

    companion object {
        fun of(vararg projects: Project): CombinedProject {
            return SimpleCombinedProject(*projects)
        }
    }
}