package kingmc.common.context.beans

/**
 * A `BeanDefinition` exposed the lifecycle of this bean
 *
 * @author kingsthere
 * @since 0.0.9
 */
interface LateinitBeanDefinition : BeanDefinition {
    /**
     * The lifecycle of this bean definition
     *
     * @since 0.0.9
     */
    val lifecycle: Int
}