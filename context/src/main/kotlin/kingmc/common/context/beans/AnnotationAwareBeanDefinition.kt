package kingmc.common.context.beans

/**
 * Extended [BeanDefinition] interface that exposes annotation about
 * its bean class
 *
 * @author kingsthere
 * @since 0.0.1
 */
interface AnnotationAwareBeanDefinition : BeanDefinition {
    /**
     * The annotation of this bean
     */
    val annotations: List<Annotation>
}