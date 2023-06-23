package kingmc.common.context.annotation

import kingmc.util.annotation.Extended
import java.lang.annotation.Inherited

/**
 * This Service annotation is to determine and inject a service bean
 * to the ioc container
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
@Extended(Component::class)
annotation class Service(
    /**
     * The bean name of this type to the container, left
     * to default it will become the decapitalized name of the class
     *
     * @see Component.name
     * @since 0.0.1
     */
    val name: String = ""
)