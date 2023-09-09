package kingmc.common.application

import kingmc.common.context.context
import kingmc.util.KingMCDsl

/**
 * Gets the application for this object
 *
 * @author kingsthere
 * @since 0.0.2
 */
@KingMCDsl
val Any.application: Application
    get() = if (context is ApplicationExposedContext) {
        (context as ApplicationExposedContext).application
    } else {
        throw IllegalStateException("This object's context did not exposed the application")
    }