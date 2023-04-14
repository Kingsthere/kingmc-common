package kingmc.common.application

/**
 * Gets a property from [currentApplication]
 *
 * @since 0.0.8
 * @author kingsthere
 */
fun getProperty(key: String): String = currentApplication().properties.getProperty(key)