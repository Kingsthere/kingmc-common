package kingmc.common.context.process

import kingmc.common.context.*
import kingmc.common.context.beans.BeanDefinition
import kingmc.common.context.beans.LateinitBeanDefinition
import kingmc.common.context.beans.annotations
import kingmc.common.context.beans.isDependencyInjectable
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
    val first = batchProcessors.indexOfFirst { it.priority > beanProcessor.priority }
    val index = if (first == -1) {
        0
    } else {
        first + 1
    }
    batchProcessors.add(index, beanProcessor)
}

/**
 * Inherit processors to from parent context
 *
 * @since 0.0.2
 * @see processors
 */
fun HierarchicalContext.inheritProcessors(parentContext: Context) {
    parentContext.processors.forEach { (lifecycle, orderedProcessors) ->
        val batchProcessors = this.processors.computeIfAbsent(lifecycle) { LinkedList() }
        orderedProcessors.forEach { beanProcessor ->
            val first = batchProcessors.indexOfFirst { it.priority > beanProcessor.priority }
            val index = if (first == -1) {
                0
            } else {
                first + 1
            }
            batchProcessors.add(index, beanProcessor)
        }
    }
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
        this.getOwningBeans().forEach {
            if (it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                processBean(it, lifecycle)
            }
        }
    } else {
        this.getBeanDefinitions().forEach {
            if (it.isDependencyInjectable()) { // Process only non-abstract singleton beans
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
 * @since 0.1.3
 * @see processors
 * @see registerProcessor
 */
fun Context.disposeBeans() {
    if (this is HierarchicalContext) {
        this.getOwningBeans().forEach {
            if (it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                disposeBean(getBeanInstance(it))
            }
        }
    } else {
        this.getBeanDefinitions().forEach {
            if (it.isDependencyInjectable()) { // Process only non-abstract singleton beans
                disposeBean(getBeanInstance(it))
            }
        }
    }
}

/**
 * Process the given bean from this context with processors that
 * is defined in this context
 *
 * @since 0.0.2
 * @see processors
 * @see registerProcessor
 * @param lifecycle the lifecycle is processing these beans
 */
fun Context.processBean(beanDefinition: BeanDefinition, lifecycle: Int) {
    if (beanDefinition is LateinitBeanDefinition && beanDefinition.lifecycle > lifecycle) {
        return // Skip lateinit bean
    }
    if (beanDefinition.annotations.hasAnnotation<IgnoreProcess>()) {
        return // Skip ignore process bean
    }
    val instance = getBeanInstance(beanDefinition)
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
}

/**
 * Dispose the given bean
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