package kingmc.common.structure

/**
 * Information of a pluggable about the packages, you
 * can specify the [excluded packages][excluded] & [included packages][included]
 *
 *
 * The priority of [excluded packages][excluded] is bigger than
 * [included packages][included]
 *
 * @since 0.0.1
 * @author kingsthere
 */
sealed interface PackageInfo {
    /**
     * The excluded packages
     *
     * @since 0.0.1
     */
    val excluded : Set<String>

    /**
     * The included packages
     *
     * @since 0.0.1
     */
    val included : Set<String>

    /**
     * Simple implement for [PackageInfo]
     *
     * @constructor create a simple implement
     * @since 0.0.1
     */
    class Implement(override val excluded: Set<String>, override val included: Set<String>) : PackageInfo

    companion object {
        /**
         * Create an instance of [PackageInfo] implemented
         * by [Implement]
         *
         * @since 0.0.1
         */
        fun create(excluded: Set<String>, included: Set<String>) : PackageInfo {
            // Create a new instance of implement and return
            return Implement(excluded.plus(DEFAULT_EXCLUDED), included.plus(DEFAULT_INCLUDED))
        }

        /**
         * The default included package of kingmc
         *
         * @since 0.0.1
         */
        private val DEFAULT_INCLUDED: Array<String> = arrayOf(
            "kingmc.common.context",
            "kingmc.platform"
        )

        val DEFAULT_EXCLUDED: Array<String> = arrayOf(
            "com.google",
            "gnu.trove",
            "junit",
            "org.yaml",
            "redis.clients.jedis",
            "kotlin",
            "de.tr7zw.nbtapi"
        )
    }
}