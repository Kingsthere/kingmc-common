package kingmc.util.key

/**
 * Something that has an associated [Key].
 *
 * @author kingsthere
 * @since 0.0.1
 * @see Key
 * @see Namespaced
 */
interface Keyed {
    /**
     * The key of this
     *
     * @since 0.0.1
     */
    val key: Key
}