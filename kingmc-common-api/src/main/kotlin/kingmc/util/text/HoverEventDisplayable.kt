package kingmc.util.text

import net.kyori.adventure.text.event.HoverEvent

/**
 * A superinterface represents an object that could
 * convert as a [HoverEvent] to apply to a text
 *
 * @author kingsthere
 * @since 0.0.3
 * @see HoverEvent
 */
interface HoverEventDisplayable {
    /**
     * Convert this object as a [HoverEvent]
     */
    fun asHoverEvent(): HoverEvent<*>
}