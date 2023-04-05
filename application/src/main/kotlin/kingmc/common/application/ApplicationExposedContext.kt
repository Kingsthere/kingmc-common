package kingmc.common.application

/**
 * A superinterface exposed the [Application] of subclasses
 *
 * @since 0.0.3
 * @author kingsthere
 */
interface ApplicationExposedContext {
    /**
     * The application of this context
     */
    val application: Application
}