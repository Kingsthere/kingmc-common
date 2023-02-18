package kingmc.common.structure

import java.io.File

/**
 * The central repository of kingmc to stores
 * all [Project] and [Pluggable] that is loaded
 * in kingmc
 *
 * @see Project
 * @since 0.0.1
 * @author kingsthere
 */
object Projects : ProjectContainer {
    private val property: Set<Project>

    /**
     * Default init code block
     *
     * @since 0.0.1
     */
    init {
        property = HashSet()
    }

    val Default : Project by lazy {
        FileProject(File(this::class.java.protectionDomain.codeSource.location.file), this::class.java.classLoader)
    }

    override fun getProjects(): Set<Project> = property
}