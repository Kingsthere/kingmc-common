package kingmc.common.context.annotation

import kingmc.common.context.beans.BeanPrivacy

/**
 * An annotation declared the privacy of the bean
 *
 * @since 0.1.0
 * @author kingsthere
 */
@Retention
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
annotation class Privacy(
    /**
     * Value of bean privacy
     */
    val privacy: BeanPrivacy = BeanPrivacy.PUBLIC
)
