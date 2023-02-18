package kingmc.util.key

/**
 * Something that has an associated [Key].
 *
 * @since 0.0.1
 * @author kingsthere
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