package kingmc.common.context.beans

/**
 * Get the annotations defined in this bean if this bean
 * definition is extended from [AnnotationAwareBeanDefinition]
 *
 * @author kingsthere
 * @since 0.0.3
 */
val BeanDefinition.annotations: List<Annotation>
    get() {
        return if (this is AnnotationAwareBeanDefinition) {
            this.annotations
        } else {
            emptyList()
        }
    }

/**
 * Get the lifecycle of this bean definition
 *
 * @author kingsthere
 * @since 0.0.9
 */
val BeanDefinition.lifecycle: Int
    get() {
        return if (this is LateinitBeanDefinition) {
            this.lifecycle
        } else {
            0
        }
    }