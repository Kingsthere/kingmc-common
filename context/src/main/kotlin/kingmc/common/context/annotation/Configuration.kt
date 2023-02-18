package kingmc.common.context.annotation

import com.ktil.annotation.Extended
import java.lang.annotation.Inherited

/**
 * Indicate that the annotated class is a configuration
 * class, configuration class is another solution for the
 * config files. You can use java/kotlin classes instead
 * of config files
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Extended(Component::class)
@Target(AnnotationTarget.CLASS)
@Retention
@MustBeDocumented
@Inherited
annotation class Configuration(
    /**
     * The bean name of this type to the container, left
     * to default it will become the decapitalized name of the class
     *
     * @since 0.0.1
     */
    val name: String = ""
)
