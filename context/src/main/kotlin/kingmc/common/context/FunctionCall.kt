package kingmc.common.context

import com.ktil.annotation.getAnnotation
import kingmc.common.context.annotation.Qualifier
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

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