package kingmc.common.context.resource

/**
 * A marker superinterface indicating that a resource eligible to be notified
 * when file changed from disk
 *
 * @since 0.0.5
 * @author kingsthere
 */
interface AutoloadAware {
    /**
     * Called when this resource load/reload
     */
    fun whenLoad()
}