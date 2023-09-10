package kingmc.common.context

import java.util.*

/**
 * Extended `BeanSource` represents a bean source with customizable properties
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface PropertyBeanSource : BeanSource {
    /**
     * The properties of this bean source
     */
    val properties: Properties
}