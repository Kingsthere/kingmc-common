package kingmc.util.annotation

import kotlin.reflect.KClass

/**
 * Annotation to declare an alias of a annotation attribute
 *
 * @since 0.0.7
 * @author kingsthere
 */
@Retention
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class AliasFor(
    /**
     * The name of the attribute to alias to
     */
    val attribute: String,

    /**
     * The annotation that hold the attribute to alias to
     */
    val annotation: KClass<out Annotation> = Annotation::class
)
