package kingmc.common.context.annotation

/**
 * Represent a bean to inject to ioc container by order, the
 * order is higher the bean will inject more prioritized
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
annotation class Ordered(
    /**
     * The value
     *
     * @since 0.0.1
     */
    val value: Byte
)
