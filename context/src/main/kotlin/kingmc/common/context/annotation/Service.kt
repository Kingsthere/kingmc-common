package kingmc.common.context.annotation

import java.lang.annotation.Inherited

/**
 * This Service annotation is to determine and inject a service bean
 * to the ioc container
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
@Component
annotation class Service(
    /**
     * The bean name of this type to the container, left
     * to default, will become the decapitalized name of the class
     *
     * @see Component.name
     * @since 0.0.1
     */
    val name: String = ""
)