package kingmc.common.application

/**
 * Gets a property from [currentApplication]
 *
 * @author kingsthere
 * @since 0.0.8
 */
fun getProperty(key: String): String = currentApplication().properties.getProperty(key)

/**
 * Gets a property or default value from [currentApplication]
 *
 * @author kingsthere
 * @since 0.0.9
 */
fun getPropertyOrElse(key: String, defaultValue: String): String =
    currentApplication().properties.getProperty(key, defaultValue)

/**
 * Gets a property or `null` from [currentApplication]
 *
 * @author kingsthere
 * @since 0.0.9
 */
fun getPropertyOrNull(key: String): String? = currentApplication().properties.getProperty(key)