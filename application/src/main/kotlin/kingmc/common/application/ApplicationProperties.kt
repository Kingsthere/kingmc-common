package kingmc.common.application

/**
 * Gets a property from [currentApplication]
 *
 * @since 0.0.8
 * @author kingsthere
 */
fun getProperty(key: String): String = currentApplication().properties.getProperty(key)

/**
 * Gets a property or default value from [currentApplication]
 *
 * @since 0.0.9
 * @author kingsthere
 */
fun getPropertyOrElse(key: String, defaultValue: String): String = currentApplication().properties.getProperty(key, defaultValue)

/**
 * Gets a property or `null` from [currentApplication]
 *
 * @since 0.0.9
 * @author kingsthere
 */
fun getPropertyOrNull(key: String): String? = currentApplication().properties.getProperty(key)