package kingmc.common.context.beans

/**
 * Extended [BeanDefinition] interface that exposes annotation about
 * its bean class
 *
 * @since 0.0.1
 * @author kingsthere
 */
interface AnnotatedBeanDefinition : BeanDefinition {
    /**
     * The annotation of this bean
     */
    val annotations: List<Annotation>
}