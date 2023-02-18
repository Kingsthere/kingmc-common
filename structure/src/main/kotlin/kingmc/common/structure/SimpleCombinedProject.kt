package kingmc.common.structure

class SimpleCombinedProject(vararg projects: Project) : CombinedProject {
    private val combined: MutableSet<Project> = projects.toMutableSet()

    /**
     * Combine a project to this
     */
    override fun combine(project: Project) {
        this.combined.add(project)
    }

    /**
     * Get a class from this project
     *
     * @return the project got, `null` if the
     *         class is not define in this project
     * @since 0.0.2
     * @author kingsthere
     */
    override fun <T : Any> getClass(name: String): Class<T>? =
        this.combined
            .map { it.getClass<T>(name) }
            .find { it != null }

    /**
     * Invoke this function to get all classes
     * in this project as a [List]
     *
     * @see Class
     * @see List
     * @since 0.0.2
     * @author kingsthere
     * @throws ProjectInitializeException when trying to initialize this project to
     *                                    get all classes in this project
     */
    override fun getClasses(): List<Class<*>> {
        val combinedClasses = mutableListOf<Class<*>>()
        this.combined.forEach { combinedClasses.addAll(it.getClasses()) }
        return combinedClasses
    }

    /**
     * Check if the specified class is defined in this project
     *
     * @since 0.0.2
     * @author kingsthere
     */
    override fun contains(clazz: Class<*>): Boolean {
        for (combined in this.combined) {
            if (clazz in combined) {
                return true
            }
        }
        return false
    }

    /**
     * Invoke this function to get all pluggable
     * in this project as a [Set]
     *
     * @see Set
     * @see Pluggable
     * @since 0.0.1
     */
    @ExperimentalStructureApi
    override fun getPluggable(): Set<Pluggable> {
        TODO("Not yet implemented")
    }
}