package kingmc.common.context.beans.depends

import kingmc.util.annotation.getAnnotation
import kingmc.util.annotation.getAnnotations
import kingmc.util.annotation.hasAnnotation
import kingmc.util.reflect.findPropertiesByAnnotation
import kingmc.common.context.BeansUtil
import kingmc.common.context.annotation.Autowired
import kingmc.common.context.annotation.Import
import kingmc.common.context.annotation.Qualifier
import kotlin.reflect.KClass

class DefaultDependencyResolver : DependencyResolver {
    override fun solveDependencyByClass(beanClass: KClass<*>, beanName: String): DependencyDescriptor {
        val solvedDependencies: MutableSet<String> = mutableSetOf()
        solveDeepDependencyFromClass(solvedDependencies, beanClass)
        return DependencyDescriptor(solvedDependencies)
    }

    private fun solveDeepDependencyFromClass(solvedDependencies: MutableSet<String>, type: KClass<*>) {
        type.findPropertiesByAnnotation<Autowired>().forEach {
            if (it.hasAnnotation<Qualifier>()) {
                solvedDependencies.add(it.getAnnotation<Qualifier>()!!.value)
            } else {
                solvedDependencies.add(BeansUtil.getBeanName(it.returnType.classifier as KClass<*>))
            }
        }
        if (type.hasAnnotation<Import>()) {
            type.getAnnotations<Import>().forEach { import ->
                import.value.forEach {
                    solveDeepDependencyFromClass(solvedDependencies, it)
                }
            }
        }
    }
}