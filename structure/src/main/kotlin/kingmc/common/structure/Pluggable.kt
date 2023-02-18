package kingmc.common.structure

/**
 * Represent a block of [Project], or a zone of codes(classes)
 * like a module in the [Project], a project could contain many
 * [Pluggable]
 *
 * @see Project
 * @since 0.0.1
 * @author kingsthere
 */
interface Pluggable {
    /**
     * Get the package info of this pluggable
     *
     * @see PackageInfo
     * @since 0.0.1
     */
    fun packages() : PackageInfo
}