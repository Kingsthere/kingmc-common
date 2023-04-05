package kingmc.common.structure

class CombinedClassSourceImpl(vararg classSources: ClassSource) : CombinedClassSource {
    private val combined: MutableSet<ClassSource> = classSources.toMutableSet()

    /**
     * Combine a project to this
     */
    override fun combine(classSource: ClassSource) {
        this.combined.add(classSource)
    }

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

    /**
     * Reload
     */
    override fun reload() {
        this.combined.forEach { it.reload() }
    }
}