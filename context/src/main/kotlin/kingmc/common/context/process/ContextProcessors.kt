package kingmc.common.context.process

import kingmc.common.context.Context
import kingmc.common.context.HierarchicalContext
import kingmc.common.context.LifecycleContext
import kingmc.common.context.beans.findSingletonBeansByType
import kingmc.common.context.beans.getOwningSingletonBeans
import kingmc.common.context.beans.getSingletonBeans
import kingmc.common.context.initializer.BeanProcessingException
import kingmc.util.InstantiateException
import kingmc.util.annotation.hasAnnotation
import java.util.*

val Context.processors: MutableMap<Byte, MutableSet<BeanProcessor>> by lazy {
    TreeMap(compareBy { it })
}

/**
 * Register a processor to this context
 *
 * @since 0.0.2
 * @see processors
 */
fun Context.registerProcessor(beanProcessor: BeanProcessor) {
    val batchProcessors = this.processors.computeIfAbsent(beanProcessor.priority) { mutableSetOf() }
    batchProcessors.add(beanProcessor)
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
 * Load all processors from this context
 *
 * @since 0.0.2
 * @see processors
 */
fun Context.loadProcessors() {
    try {
        val processorInstance = findSingletonBeansByType(BeanProcessor::class)
        processorInstance
            .map { getBeanInstance(it) as BeanProcessor }
            .forEach { registerProcessor(it) }
    } catch (e: ClassCastException) {
        throw ProcessorInitializeException("A processor must assignable from interface BeanProcessor", e)
    } catch (e: InstantiateException) {
        throw ProcessorInitializeException("Unable to instantiate processor", e)
    }
}

/**
 * Process all beans from this context with processors that
 * is defined in this context, note if this [Context] is a [HierarchicalContext]
 * this context will only process the **protected beans**
 *
 * @since 0.0.2
 * @see processors
 * @see registerProcessor
 * @param bean the bean to process
 * @param lifecycle the lifecycle is processing these beans
 */
fun Context.processBeans(lifecycle: Int) {
    if (this is HierarchicalContext) {
        this.getOwningSingletonBeans().forEach { processBean(it, lifecycle) }
    } else {
        this.getSingletonBeans().forEach { processBean(it, lifecycle) }
    }
}

/**
 * Process a beans from this context with processors that
 * is defined in this context
 *
 * @since 0.0.2
 * @see processors
 * @see registerProcessor
 * @param bean the bean to process
 * @param lifecycle the lifecycle is processing these beans
 */
fun Context.processBean(instance: Any, lifecycle: Int) {
    if (instance::class.hasAnnotation<IgnoreProcess>()) {
        return
    }
    for (batchPriorityProcessor in processors) {
        for (beanProcessor in batchPriorityProcessor.value) {
            if (beanProcessor.lifecycle == lifecycle) {
                try {
                    beanProcessor.process(this, instance)
                } catch (e: Exception) {
                    throw BeanProcessingException("An exception occurred while processing bean $instance (lifecycle: $lifecycle processor: $beanProcessor)", e, instance)
                }
            }
        }
    }
}

/**
 * Call after processing for every bean processors
 *
 * @since 0.0.2
 * @param lifecycle the lifecycle is processing these beans
 * @see processors
 * @see processBean
 */
fun Context.afterProcess(lifecycle: Int) {
    for (batchPriorityProcessor in processors) {
        for (beanProcessor in batchPriorityProcessor.value) {
            if (beanProcessor.lifecycle == lifecycle) {
                try {
                    beanProcessor.afterProcess(this)
                } catch (e: Exception) {
                    throw BeanProcessingException("An exception occurred while end processing by processor $beanProcessor (processor: $beanProcessor lifecycle: $lifecycle)", e, beanProcessor)
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
}

/**
 * Call after processing for every bean processors
 *
 * @since 0.0.2
 * @see processors
 * @see processBean
 */
fun Context.afterDispose() {
    for (batchPriorityProcessor in processors) {
        for (beanProcessor in batchPriorityProcessor.value) {
            beanProcessor.afterProcess(this)
        }
    }
}

/**
 * Insert lifecycle for process beans using the bean
 * processor in this context
 *
 * @since 0.0.4
 * @author kingsthere
 */
fun LifecycleContext.insertProcessBeanLifecycle(lifecycleLength: Int) {
    for (index in 0..lifecycleLength) {
        this.lifecycle().insertPlan(index) {
            try {
                this.processBeans(index)
                this.afterProcess(index)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}