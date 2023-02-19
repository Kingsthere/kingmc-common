package kingmc.common.context.resource

import kingmc.util.annotation.Extendable

/**
 * [Resource] annotation to declare a resource required by extension
 *
 * @since 0.0.5
 * @author kingsthere
 */
@Extendable
@Retention
@Target(AnnotationTarget.CLASS)
annotation class Resource(
    /**
     * The provider to provides the default resource when this resource is not exists
     */
    val provider: ResourceProvider,

    /**
     * The value of provider
     */
    val providerValue: String
)
