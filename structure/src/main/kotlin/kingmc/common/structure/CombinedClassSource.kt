package kingmc.common.structure

/**
 * Represent a project that is combined from other
 * projects
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface CombinedClassSource : ClassSource {
    /**
     * Combine a project to this
     */
    fun combine(classSource: ClassSource)

    companion object {
        fun of(vararg classSources: ClassSource): CombinedClassSource {
            return CombinedClassSourceImpl(*classSources)
        }
    }
}