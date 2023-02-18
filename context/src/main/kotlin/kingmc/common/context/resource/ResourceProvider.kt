package kingmc.common.context.resource

/**
 * Enumerate providers of resource when resource is not find
 *
 * @since 0.0.5
 * @author kingsthere
 */
enum class ResourceProvider {
    /**
     * Jar - to load resources from jar file
     *
     *
     * For example `default/config.yml`
     */
    JAR,

    /**
     * URL - load resources from url
     *
     *
     * For example: `file://C:/Users/config.yml`
     */
    URL
}