package kingmc.util.lifecycle

import java.util.*

/**
 * A simple implementation of [Lifecycle]
 *
 * @author kingsthere
 * @since 0.1.2
 * @see Lifecycle
 */
class LifecycleImpl(val length: Int) : Lifecycle {
    private val _scheduledExecutions: Array<MutableList<Execution>> = Array(length) { LinkedList() }
    private var _cursor = 0

    /**
     * Gets the current lifecycle stage
     */
    override val cursor: Int
        get() = _cursor

    /**
     * Schedule the given execution to this lifecycle
     *
     * @param lifecycle the lifecycle to execute the execution, must be non-negative
     * @param execution the execution to schedule
     */
    override fun scheduleExecution(lifecycle: Int, execution: Execution) {
        // Check if lifecycle stage is already passed
        check(lifecycle >= _cursor) { "Lifecycle $lifecycle is already passed (current: $_cursor)" }

        val list = _scheduledExecutions[lifecycle]
        // Find the position to insert
        val first = list.indexOfFirst { it.priority > execution.priority }
        val index = if (first == -1) {
            0
        } else {
            first + 1
        }
        list.add(index, execution)
    }

    private fun executeScheduledExecutions(lifecycle: Int) {
        val list = _scheduledExecutions[lifecycle]
        // Pop out executions scheduled in this lifecycle with the
        // given lifecycle and execute them
        list.forEach {
            it.execute()
        }
        list.clear()
    }

    /**
     * Go to next lifecycle stage and execute all executions scheduled in this lifecycle
     */
    override fun next() {
        _cursor++
        executeScheduledExecutions(_cursor - 1)
    }
}