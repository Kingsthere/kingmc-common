package kingmc.common.context.beans

/**
 * A `BeanDefinition` exposed the lifecycle of this bean
 *
 * @since 0.0.9
 * @author kingsthere
 */
interface LateinitBeanDefinition : BeanDefinition {
    /**
     * The lifecycle of this bean definition
     *
     * @since 0.0.9
     */
    val lifecycle: Int
}