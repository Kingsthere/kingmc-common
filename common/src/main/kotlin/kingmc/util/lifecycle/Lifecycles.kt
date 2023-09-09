package kingmc.util.lifecycle


/**
 * An object declared general lifecycle stages of a minecraft server
 *
 * @author kingsthere
 * @since 0.1.2
 */
object Lifecycles {
    /**
     * Lifecycle being setup
     */
    const val CONST: Int = 0

    /**
     * The server starts to initialize
     */
    const val INITIALIZE: Int = 1

    /**
     * The server starts to load
     */
    const val LOAD: Int = 2

    /**
     * The server fully started and ready to accept players join
     */
    const val ACTIVE: Int = 3

    /**
     * The server is shutting down
     */
    const val SHUTDOWN: Int = 4

    /**
     * The default length of a lifecycle (0-4)
     */
    const val DEFAULT_LENGTH = 5
}

/**
 * Schedule the given execution at [Lifecycles.CONST]
 *
 * @param priority the priority to run the execution
 * @param body the body of the execution to run
 */
fun Lifecycle.onConst(priority: Byte = 0, name: String? = null, body: () -> Unit) = scheduleExecution(Lifecycles.CONST, Execution(priority, name, body))

/**
 * Schedule the given execution at [Lifecycles.INITIALIZE]
 *
 * @param priority the priority to run the execution
 * @param body the body of the execution to run
 */
fun Lifecycle.onInitialize(priority: Byte = 0, name: String? = null, body: () -> Unit) = scheduleExecution(Lifecycles.INITIALIZE, Execution(priority, name, body))

/**
 * Schedule the given execution at [Lifecycles.LOAD]
 *
 * @param priority the priority to run the execution
 * @param body the body of the execution to run
 */
fun Lifecycle.onLoad(priority: Byte = 0, name: String? = null, body: () -> Unit) = scheduleExecution(Lifecycles.LOAD, Execution(priority, name, body))

/**
 * Schedule the given execution at [Lifecycles.ACTIVE]
 *
 * @param priority the priority to run the execution
 * @param body the body of the execution to run
 */
fun Lifecycle.onActive(priority: Byte = 0, name: String? = null, body: () -> Unit) = scheduleExecution(Lifecycles.ACTIVE, Execution(priority, name, body))

/**
 * Schedule the given execution at [Lifecycles.SHUTDOWN]
 *
 * @param priority the priority to run the execution
 * @param body the body of the execution to run
 */
fun Lifecycle.onShutdown(priority: Byte = 0, name: String? = null, body: () -> Unit) = scheduleExecution(Lifecycles.SHUTDOWN, Execution(priority, name, body))

/**
 * Create an instance of `Lifecycle` and return
 *
 * @return the lifecycle instance created
 */
fun Lifecycle(length: Int = Lifecycles.DEFAULT_LENGTH): Lifecycle = LifecycleImpl(length)