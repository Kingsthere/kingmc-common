package kingmc.util.lifecycle

/**
 * A functional interface represents an execution scheduled to a `Lifecycle` instance
 *
 * @author kingsthere
 * @since 0.1.2
 */
fun interface Execution {
    /**
     * The priority of this execution, for executions that have the same priority, the order
     * to execute them is the time they scheduled to lifecycle, the last scheduled one runs
     * latest, and the first scheduled one runs first
     */
    val priority: Byte get() = 0

    /**
     * The name of this execution
     */
    val name: String? get() = null

    /**
     * Execute this execution
     */
    fun execute()
}

/**
 * A shortcut to create a `Execution` with the given priority
 *
 * @param priority the priority of the execution
 * @param body the body to be executed for the execution
 * @return the execution instance created
 * @author kingsthere
 * @since 0.1.2
 */
fun Execution(priority: Byte = 0, name: String? = null, body: () -> Unit): Execution {
    return object : Execution {
        override val priority: Byte get() = priority
        override val name: String? get() = name

        override fun execute() {
            body()
        }

        override fun toString(): String {
            return "$name(priority=$priority)"
        }

    }
}