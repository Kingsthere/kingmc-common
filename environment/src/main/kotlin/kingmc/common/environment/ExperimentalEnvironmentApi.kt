package kingmc.common.environment

/**
 * To mark a experimental environment api
 *
 * @since 0.0.6
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
@MustBeDocumented
annotation class ExperimentalEnvironmentApi
