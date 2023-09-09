package kingmc.common.context.process

import kingmc.common.context.Context
import kingmc.common.context.HierarchicalContext
import kingmc.common.context.LifecycleContext
import kingmc.common.context.beans.*
import kingmc.common.context.exception.BeanProcessingException
import kingmc.common.context.exception.ProcessorInitializeException
import kingmc.util.InstantiateException
import kingmc.util.annotation.hasAnnotation
import kingmc.util.lifecycle.Execution
import java.util.*
import kotlin.reflect.full.isSubclassOf

/**
 * A shortcut to get [ProcessorContext.processors] of this context
 */
val Context.processors: MutableMap<Int, MutableList<BeanProcessor>>
    get() = (this as ProcessorContext).processors

/**
 * Register a processor to this context
 *
 * @since 0.0.2
 * @see processors
 */
fun Context.registerProcessor(beanProcessor: BeanProcessor) {
    val batchProcessors = this.processors.computeIfAbsent(beanProcessor.lifecycle) { LinkedList() }
    batchProcessors.add(beanProcessor)
    batchProcessors.sortByDescending { it.priority }
}

/**
 * Inherit processors to from parent context
 *
 * @since 0.0.2
 * @see processors
 */
fun HierarchicalContext.inheritProcessors(parentContext: Context) {
    processors.putAll(parentContext.processors)
}

/**
 * Remove the given processor from this context
 *
 * @since 0.1.2
 */
fun Context.removeProcessor(processor: BeanProcessor) {
    processors[processor.lifecycle]?.remove(processor)
}

/**
 * Load all processors from this context
 *
 * @since 0.0.2
 * @see processors
 */
fun Context.loadProcessors() {
    try {
        if (this is HierarchicalContext) {
            this.getProtectedBeans()
        } else {
            this.getBeanDefinitions()
        }.forEach {
            if (it.isDependencyInjectable() && it.type.isSubclassOf(BeanProcessor::class)) {
                registerProcessor(getBeanInstance(it) as BeanProcessor)
            }
        }
    } catch (e: ClassCastException) {
        throw ProcessorInitializeException("A processor must assignable from interface BeanProcessor", e)
    } catch (e: InstantiateException) {
        throw ProcessorInitializeException("Failed to instantiate processor", e)
    }
}

/**
 * Process all beans from this context with processors that
 * is defined in this context, note if this [Context] is a [HierarchicalContext]
 * this context will only process the **protected beans**
 *
 * @since 0.1.1
 * @see processors
 * @see registerProcessor
 * @param lifecycle the lifecycle is processing these beans
 */
fun Context.processBeans(lifecycle: Int) {
    if (this is HierarchicalContext) {
        this.getProtectedBeans().forEach {
            if (!it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                processBean(it, lifecycle)
            }
        }
    } else {
        this.getBeanDefinitions().forEach {
            if (!it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                processBean(it, lifecycle)
            }
        }
    }
}

/**
 * Dispose all beans from this context with processors that
 * is defined in this context, note if this [Context] is a [HierarchicalContext]
 * this context will only process the **protected beans**
 *
 * @since 0.1.1
 * @see processors
 * @see registerProcessor
 */
fun Context.disposeBeans() {
    if (this is HierarchicalContext) {
        this.getProtectedBeans().forEach {
            if (!it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                disposeBean(it)
            }
        }
    } else {
        this.getBeanDefinitions().forEach {
            if (!it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                disposeBean(it)
            }
        }
    }
}

/**
 * Process a beans from this context with processors that
 * is defined in this context
 *
 * @since 0.0.2
 * @see processors
 * @see registerProcessor
 * @param lifecycle the lifecycle is processing these beans
 */
fun Context.processBean(instance: Any, lifecycle: Int) {
    if (instance::class.hasAnnotation<IgnoreProcess>()) {
        return
    }
    for (beanProcessor in processors[lifecycle] ?: emptySet()) {
        if (beanProcessor.lifecycle == lifecycle) {
            try {
                beanProcessor.process(this, instance)
            } catch (e: Exception) {
                throw BeanProcessingException(
                    "An exception occurred while processing bean $instance (lifecycle: $lifecycle processor: $beanProcessor)",
                    e,
                    instance,
                    beanProcessor
                )
            }
        }
    }
    if (this is HierarchicalContext) {
        this.listParents().forEach { parent ->
            for (beanProcessor in parent.processors[lifecycle] ?: emptySet()) {
                if (beanProcessor.lifecycle == lifecycle) {
                    try {
                        beanProcessor.process(this, instance)
                    } catch (e: Exception) {
                        throw BeanProcessingException(
                            "An exception occurred while processing bean $instance (lifecycle: $lifecycle processor: $beanProcessor)",
                            e,
                            instance,
                            beanProcessor
                        )
                    }
                }
            }
        }
    }
}

/**
 * Call after processing for every bean processor
 *
 * @since 0.0.2
 * @param lifecycle the lifecycle is processing these beans
 * @see processors
 * @see processBean
 */
fun Context.afterProcess(lifecycle: Int) {
    for (beanProcessor in processors[lifecycle] ?: emptySet()) {
        if (beanProcessor.lifecycle == lifecycle) {
            try {
                beanProcessor.afterProcess(this)
            } catch (e: Exception) {
                throw BeanProcessingException(
                    "An exception occurred while end processing by processor $beanProcessor (processor: $beanProcessor lifecycle: $lifecycle)",
                    e,
                    null,
                    beanProcessor
                )
            }
        }
    }
    if (this is HierarchicalContext) {
        this.listParents().forEach { parent ->
            for (beanProcessor in parent.processors[lifecycle] ?: emptySet()) {
                if (beanProcessor.lifecycle == lifecycle) {
                    try {
                        beanProcessor.afterProcess(this)
                    } catch (e: Exception) {
                        throw BeanProcessingException(
                            "An exception occurred while end processing by processor $beanProcessor (processor: $beanProcessor lifecycle: $lifecycle)",
                            e,
                            null,
                            beanProcessor
                        )
                    }
                }
            }
        }
    }
}

/**
 * Dispose a loaded bean
 *
 * @since 0.0.2
 * @see processors
 * @see processBean
 */
fun Context.disposeBean(instance: Any) {
    for (batchPriorityProcessor in processors) {
        for (beanProcessor in batchPriorityProcessor.value) {
            beanProcessor.dispose(this, instance)
        }
    }
    if (this is HierarchicalContext) {
        this.listParents().forEach { parent ->
            for (batchPriorityProcessor in parent.processors) {
                for (beanProcessor in batchPriorityProcessor.value) {
                    beanProcessor.dispose(this, instance)
                }
            }
        }
    }
}

/**
 * Call after processing for every bean processor
 *
 * @since 0.0.2
 * @see processors
 * @see processBean
 */
fun Context.afterDispose() {
    for (batchPriorityProcessor in processors) {
        for (beanProcessor in batchPriorityProcessor.value) {
            beanProcessor.afterDispose(this)
        }
    }
    if (this is HierarchicalContext) {
        this.listParents().forEach { parent ->
            for (batchPriorityProcessor in parent.processors) {
                for (beanProcessor in batchPriorityProcessor.value) {
                    beanProcessor.afterDispose(this)
                }
            }
        }
    }
}

/**
 * Insert lifecycle for process beans using the bean
 * processor in this context
 *
 * @author kingsthere
 * @since 0.0.4
 */
fun LifecycleContext.insertProcessBeanLifecycle(lifecycleLength: Int) {
    for (index in 0..lifecycleLength) {
        this.getLifecycle().scheduleExecution(index, Execution(1, "process") {
            try {
                this.processBeans(index)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }
}

/**
 * Insert lifecycle for after process beans using the bean
 * processor in this context
 *
 * @author kingsthere
 * @since 0.0.4
 */
fun LifecycleContext.insertAfterProcessBeanLifecycle(lifecycleLength: Int) {
    for (index in 0..lifecycleLength) {
        this.getLifecycle().scheduleExecution(index, Execution(-1, "after_process") {
            this.afterProcess(index)
        })
    }
}