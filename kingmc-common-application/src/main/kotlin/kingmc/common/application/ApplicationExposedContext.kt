package kingmc.common.application

/**
 * A superinterface exposed the [Application] of subclasses
 *
 * @author kingsthere
 * @since 0.0.3
 */
interface ApplicationExposedContext {
    /**
     * The application of this context
     */
    val application: Application
}