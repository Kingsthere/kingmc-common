package kingmc.common.context.resource

import kotlin.reflect.KClass

/**
 * Enumerate sources of resource when resource is not find
 *
 * @param TValue the kind of value to use this type of source and get the actual resource
 * @since 0.0.5
 * @author kingsthere
 */
sealed class ResourceSource<TValue>(val valueClass: KClass<*>) {
    /**
     * Jar - to load resources from jar file
     *
     *
     * For example `default/config.yml`
     */
    data object JAR : ResourceSource<String>(String::class)

    /**
     * URL - load resources from url
     *
     *
     * For example: `file://C:/Users/config.yml`
     */
    data object URL : ResourceSource<URL>(URL::class)

    companion object {
        fun values(): Array<ResourceSource<*>> {
            return arrayOf(JAR, URL)
        }

        fun valueOf(value: String): ResourceSource<*> {
            return when (value) {
                "JAR" -> JAR
                "URL" -> URL
                else -> throw IllegalArgumentException("No object kingmc.common.context.resource.ResourceSource.$value")
            }
        }
    }
}