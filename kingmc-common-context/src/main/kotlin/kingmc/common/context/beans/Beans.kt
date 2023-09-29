package kingmc.common.context.beans

/**
 * Check if this bean definition is an injectable bean, an injectable bean means the
 * bean can be dependency injected, an injectable bean must:
 *  + Only have one instance in a contexts
 *  + Not an abstract bean
 *  + Declared as a class, not by `@Bean`
 *
 * @author kingsthere
 * @since 0.1.2
 */
fun BeanDefinition.isDependencyInjectable(): Boolean {
    return scope != BeanScope.PROTOTYPE && !isAbstract() && this is ScannedBeanDefinition
}