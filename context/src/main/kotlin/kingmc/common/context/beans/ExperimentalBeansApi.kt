package kingmc.common.context.beans

/**
 * To mark a experimental bean api
 *
 * @since 0.0.4
 * @author kingsthere
 */
@Retention
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@RequiresOptIn
annotation class ExperimentalBeansApi
