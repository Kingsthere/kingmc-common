package kingmc.common.context

import kingmc.common.context.annotation.Qualifier
import kingmc.util.annotation.getAnnotation
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

/**
 * Call a [function] with context, argument(dependencies) to call the function will be injected from receiver context
 *
 * @receiver the context to call function
 * @param function the function to call
 * @param ins the instance to call the function
 */
fun <R> Context.callFunctionWithContext(function: KFunction<R>, ins: Any?): R {
    val parameters = function.parameters
    if (parameters.size != 1) {
        val args: MutableMap<KParameter, Any?> = mutableMapOf()
        parameters.forEach {
            if (it.index != 0) {
                val beanClass = it.type.classifier as KClass<*>
                val beanName = it.getAnnotation<Qualifier>()?.value
                args[it] = if (beanName != null) {
                    this.getBean(beanName)
                } else {
                    this.getBean(beanClass)
                }
            } else {
                args[it] = ins
            }
        }
        return function.callBy(args)
    } else {
        return function.call(ins)
    }
}