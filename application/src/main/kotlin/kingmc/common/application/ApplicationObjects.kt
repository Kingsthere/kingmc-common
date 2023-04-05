package kingmc.common.application

import kingmc.common.context.context
import kingmc.util.KingMCDsl

/**
 * The application defined of current object's type
 *
 * @since 0.0.2
 * @author kingsthere
 */
@KingMCDsl
val Any.application: Application
    get() = if (context is ApplicationExposedContext) {
        (context as ApplicationExposedContext).application
    } else {
        throw IllegalStateException("This object's context did not exposed the application")
    }