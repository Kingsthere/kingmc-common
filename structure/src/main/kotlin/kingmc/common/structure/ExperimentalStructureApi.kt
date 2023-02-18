package kingmc.common.structure

/**
 * Indicate a experimental structure api
 *
 * @since 0.0.2
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@RequiresOptIn
annotation class ExperimentalStructureApi
