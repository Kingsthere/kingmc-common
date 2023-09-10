package kingmc.common.context.beans

/**
 * An enum determining the privacy of beans, this is used to isolate beans
 * in different contexts to avoid bean implementation collisions for hierarchical contexts
 *
 * @author kingsthere
 * @since 0.1.0
 */
enum class BeanPrivacy {
    /**
     * The bean can only access the context that defined that bean
     */
    PRIVATE,

    /**
     * The bean can access by child contexts
     */
    PUBLIC
}