package kingmc.util.lifecycle


/**
 * An interface represents a `Lifecycle` that provide apis for 3rd applications to schedule
 * execution on the given stage while a minecraft server running
 *
 * The initial lifecycle stages starts from 0
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface Lifecycle {
    /**
     * Gets the current lifecycle stage
     */
    val cursor: Int

    /**
     * Schedule the given execution to this lifecycle
     *
     * @param lifecycle the lifecycle to execute the execution, must be non-negative
     * @param execution the execution to schedule
     */
    fun scheduleExecution(lifecycle: Int, execution: Execution)

    /**
     * Go to the next lifecycle stage and execute all executions scheduled in this lifecycle
     */
    fun next()
}