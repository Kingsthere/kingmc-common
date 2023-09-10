package kingmc.util

/**
 * A superinterface for objects that could contain many tags, tag
 * is a lightweight mark to generic objects for quickly defining them
 *
 * @author kingsthere
 * @since 0.0.1
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
 * Check if this tagged object has a tag
 *
 * @author kingsthere
 * @since 0.0.2
 */
fun Tagged.hasTag(tag: String): Boolean =
    this.tags.contains(tag)