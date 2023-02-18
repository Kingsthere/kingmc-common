package kingmc.util

/**
 * A superinterface for objects that could contain many tags, tag
 * is a lightweight mark to generic objects for quickly defining them
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface Tagged {
    /**
     * The tags of this tagged object
     *
     * @since 0.0.1
     */
    val tags: Set<String>
}

/**
 * Check if this tagged object have a tag
 *
 * @since 0.0.2
 * @author kingsthere
 */
fun Tagged.hasTag(tag: String): Boolean =
    this.tags.contains(tag)