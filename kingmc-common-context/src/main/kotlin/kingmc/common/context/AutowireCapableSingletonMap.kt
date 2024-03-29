package kingmc.common.context

import kingmc.common.context.annotation.Autowired
import kingmc.util.SingletonMap
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor

/**
 * Extend [SingletonMap] provide instances implements dependency injection by @Autowired constructors
 *
 * @author kingsthere
 * @since 0.1.0
 */
open class AutowireCapableSingletonMap(val context: Context) : SingletonMap() {
    override fun <T : Any> createInstance(clazz: KClass<out T>): T {
        val primaryConstructor = clazz.primaryConstructor
        return if (primaryConstructor?.hasAnnotation<Autowired>() == true) {
            context.callFunctionWithContext(primaryConstructor, null)
        } else {
            super.createInstance(clazz)
        }
    }
}