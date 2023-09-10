package kingmc.common.context.annotation

/**
 * Indicate that the annotated types is a component that will inject into the ioc
 * container when the application starting
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Target(AnnotationTarget.CLASS)
@Retention
@MustBeDocumented
annotation class Component(
    /**
     * The bean name of this type to the container, left
     * to default, will become the decapitalized name of the class
     *
     * @since 0.0.1
     */
    val name: String = ""
)