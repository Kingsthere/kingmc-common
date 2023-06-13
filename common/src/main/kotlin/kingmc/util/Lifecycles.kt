package kingmc.util

import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.Multimap
import kingmc.util.builder.Buildable
import kingmc.util.errorprone.CanIgnoreReturnValue
import org.jetbrains.annotations.Contract

/**
 * Simple implement of [Lifecycle] that
 * commonly used
 *
 * @author kingsthere
 * @see Lifecycle
 *
 * @since 0.0.1
 */
open class SimpleLifecycle<T : Runnable?>

/**
 * Create a lifecycle with actions, actions are get from [List], you can
 * use builder to easily build a lifecycle
 *
 * @param plans the lifecycle actions as [Multimap][com.google.common.collect.Multimap]
 * @since 0.0.1
 */ (private val plans: Multimap<Int, T>) : Lifecycle<T> {
    // Set up the cursor
    private var cursor = 0

    override fun iterator(): Iterator<T> =
        plans.values().iterator()

    override fun reset() {
        // Reset the cursor
        cursor = 0
    }

    override fun next(): Collection<T> {
        // Get actions to run from plans
        val action = plans[cursor]
        // Run the actions
        for (item in action) {
            item!!.run()
        }
        // Add the cursor
        cursor++
        // Return the actions
        return action
    }

    override fun jump(step: Int) {
        for (index in 1..step) {
            next()
        }
    }

    override fun back(step: Int) {
        for (index in 1..step) {
            // Send back the cursor
            cursor--
            // Get actions to run from plans list
            val action = plans[cursor]
            // Run the actions

            for (item in action) {
                if (item is RepeatableRunnable) {
                    (item as RepeatableRunnable).back()
                }
            }
        }
    }

    override fun addPlan(action: T) {
        plans.put(cursor, action)
    }

    override fun insertPlan(stage: Int, action: T) {
        if (this.cursor >= stage) {
            action?.run()
            return
        }
        plans.put(stage, action)
    }

    override fun removeStage(stage: Int) {
        plans.removeAll(stage)
    }

    /**
     * Build an instance of [SimpleLifecycle]
     *
     * @param T the type of the action
     * @since 0.0.1
     */
    class Builder<T : Runnable?> : Lifecycle.Builder<T> {
        private val plans: Multimap<Int, T> = LinkedListMultimap.create()
        override fun plan(stage: Int, action: T): Lifecycle.Builder<T> {
            plans.put(stage, action)
            return this
        }

        override fun build(): Lifecycle<T> {
            return SimpleLifecycle(plans)
        }
    }

    override fun cursor(): Int {
        return this.cursor
    }

    override fun clear() {
        this.plans.clear()
    }
}

/**
 * Classes implement from this interface have the feature that can go back and run again
 * the lifecycle should call [RepeatableRunnable.back]
 * when the lifecycle jump back
 *
 * @see Runnable
 *
 * @author kingsthere
 * @since 0.0.1
 */
interface RepeatableRunnable : Runnable {
    /**
     * Called when a [Lifecycle] jump back
     *
     * @since 0.0.1
     */
    fun back()
}

/**
 * Represent a lifecycle of a program/process, for example: bedwars is
 * a lifecycle, it has many stage like [start, upgrade resource spawners..., end, repeat]
 *
 *
 * The life cycle is like a line or a [Multimap], from start to end. it has many stage between this
 * line, the stages have many action when reach the stage(we called it lifecycle
 * action([Runnable]), the cursor represent the current
 * lifecycle action. simply use [Lifecycle.next] to move the cursor to next stage and
 * run the lifecycle action in next stage.
 * All the lifecycle actions behind the cursor should all run at least **one time** already.
 *
 *
 * The lifecycles can jump to any stage, mostly use [Lifecycle.jump] to jump to stages
 * that in front. if you want the lifecycle jump back to any stage, you can use [Lifecycle.back]
 *
 *
 * The lifecycle should be repeatable, use [Lifecycle.reset] to reset the lifecycle, so
 * when the lifecycle will run again from the start.
 *
 *
 * Also, you can use [LifecycleHandler] as the standard booster to boot lifecycle.
 *
 * @see LifecycleHandler
 * @implNote Most of the implements should support concurrent
 * @param T the type of the lifecycle actions that can planned
 * in this lifecycle
 * @author kingsthere
 * @since 0.0.1
 */
interface Lifecycle<T : Runnable?> : Iterable<T> {
    /**
     * Reset this lifecycle, just like set cursor to 0 again.
     *
     * @since 0.0.1
     */
    fun reset()

    /**
     * Run and return the next lifecycle action, the cursor will automatically move to next
     * stage.
     *
     * @return lifecycle action ran
     * @since 0.0.1
     */
    @CanIgnoreReturnValue
    operator fun next(): Collection<T>

    /**
     * Jump to the next few lifecycle actions, all the lifecycle actions passed will run.
     *
     * @param step the steps to jump
     * @since 0.0.1
     */
    fun jump(step: Int)

    /**
     * Jump back few lifecycle actions, if the lifecycle actions that passed implement
     * [RepeatableRunnable] it will automatically call [RepeatableRunnable.back]
     * so you can restore consequence when run the actions
     *
     * @param step the steps to jump
     * @since 0.0.1
     */
    fun back(step: Int)

    /**
     * Add a new plan to the end of this lifecycle
     *
     * @param action the action to add
     * @throws UnsupportedOperationException if the lifecycle is immutable
     * @since 0.0.1
     */
    fun addPlan(action: T)

    /**
     * Insert a plan to this lifecycle in a present stage, if this
     * lifecycle is already reach over the stage then the stage
     * inserted will **automatically run**.
     *
     * @param action the action to insert
     * @param stage the stage to insert, or the cursor where action should run
     * @throws UnsupportedOperationException if the lifecycle is immutable
     * @since 0.0.1
     */
    fun insertPlan(stage: Int, action: T)

    /**
     * Remove all actions in a stage
     *
     * @param stage the stage to remove
     * @throws UnsupportedOperationException if the lifecycle is immutable
     * @since 0.0.1
     */
    fun removeStage(stage: Int)

    /**
     * Get the current cursor this lifecycle on
     *
     * @since 0.0.1
     */
    fun cursor(): Int

    /**
     * Clear all plans added into this lifecycle
     *
     * @since 0.0.1
     */
    fun clear()

    /**
     * Build/Plan a [Lifecycle] in an easy way
     *
     * @since 0.0.1
     */
    interface Builder<T : Runnable?> : Buildable.Builder<Lifecycle<T>?> {
        /**
         * Plan a lifecycle action to product
         *
         * @param stage the stage to trig the action
         * @param action the action to plan
         * @return this
         */
        fun plan(stage: Int, action: T): Builder<T>
    }

    companion object {
        /**
         * Create and return a new builder
         *
         * @param E the type of the lifecycle action
         * @return the builder newed
         */
        @JvmStatic
        @Contract(" -> new")
        fun <E : Runnable> builder(): Builder<E> {
            return SimpleLifecycle.Builder()
        }

        /**
         * Create an empty lifecycle and return
         *
         * @param E the type of the lifecycle action
         * @return the builder newed
         */
        fun <E : Runnable> create(): Lifecycle<E> {
            return SimpleLifecycle(LinkedListMultimap.create())
        }
    }
}

/**
 * Create and return a new builder
 *
 * @param E the type of the lifecycle action
 * @return the builder newed
 */
fun <E : Runnable> lifecycle(): Lifecycle<E> {
    return Lifecycle.create()
}

/**
 * Create an empty lifecycle and return
 *
 * @param E the type of the lifecycle action
 * @return the builder newed
 */
fun <E : Runnable> lifecycleBuilder(): Lifecycle.Builder<E> {
    return Lifecycle.builder()
}

/**
 * Represent an object that can handle any instance
 * of [Lifecycle], usually it represents to boot
 * up lifecycle to reach stages on times.
 *
 *
 * The object that implement from this interface should
 * have a [Lifecycle] as the target to handle. Use method
 * [getLifecycle] to get the target lifecycle it's handling,
 * the target lifecycle it's handling should is **immutable**,
 * and a [LifecycleHandler] should only handle **one lifecycle** in
 * **one instance**.
 *
 *
 * @see Lifecycle
 * @see Runnable
 * @param T the type of lifecycle action that
 *          stores in lifecycle
 * @since 0.0.1
 * @author kingsthere
 */
interface LifecycleHandler<T : Runnable> {
    /**
     * Get the lifecycle this handler handling, the
     * return value should be immutable.
     *
     * @since 0.0.1
     */
    fun getLifecycle(): Lifecycle<T>
}