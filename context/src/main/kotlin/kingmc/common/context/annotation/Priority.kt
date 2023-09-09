package kingmc.common.context.annotation

/**
 * Represent a bean to inject to ioc container by order, the
 * order is higher the bean will inject more prioritized
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
@MustBeDocumented
annotation class Priority(
    /**
     * The priority of the bean
     *
     * @since 0.0.1
     */
    val priority: Byte
)
