package kingmc.common.context

import java.util.Properties

/**
 * Indicates a property source, implement this interface for a bean will
 * apply the properties from this source to the context
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface PropertySource {
    /**
     * The properties provided by this property source
     */
    val properties: Properties
}