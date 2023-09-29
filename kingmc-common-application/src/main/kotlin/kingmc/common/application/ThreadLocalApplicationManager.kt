package kingmc.common.application


/**
 * An application manager implemented by [ThreadLocal]
 *
 * @author kingsthere
 * @since 0.0.3
 */
object ThreadLocalApplicationManager {
    private val threadLocal = ThreadLocal<Application>()

    /**
     * Return the application instance in current context
     */
    fun currentOrNull(): Application? {
        return threadLocal.get()
    }

    fun bindApplicationToThread(application: Application?) {
        if (application != null) {
            threadLocal.set(application)
        } else {
            threadLocal.remove()
        }
    }
}