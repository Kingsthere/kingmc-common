package kingmc.common.context.annotation

import com.ktil.annotation.Extendable
import java.lang.annotation.Inherited

/**
 * Indicate that the annotated types is a
 * component that will inject into the ioc
 * container when application starting. After you
 * can find them from the ioc container
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Target(AnnotationTarget.CLASS)
@Retention
@MustBeDocumented
@Inherited
@Extendable
annotation class Component (
    /**
     * The bean name of this type to the container, left
     * to default it will become the decapitalized name of the class
     *
     * @since 0.0.1
     */
    val name: String = ""
)