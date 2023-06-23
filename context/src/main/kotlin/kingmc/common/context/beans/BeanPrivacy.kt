package kingmc.common.context.beans

/**
 * An enum determining the privacy of beans, this is used to isolate beans
 * in different contexts to avoid bean implementation collisions for hierarchical contexts
 *
 * @since 0.1.0
 * @author kingsthere
 */
enum class BeanPrivacy {
    /**
     * The bean can only access for context that defined that bean
     */
    PRIVATE,

    /**
     * The bean can access by child contexts
     */
    PUBLIC
}