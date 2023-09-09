package kingmc.common.application

import java.lang.annotation.Inherited

/**
 * Marker annotation for every instance that separated by applications, when the
 * application of instances disposed, instances will dispose automatically
 *
 * @author kingsthere
 * @since 0.0.7
 */
@Retention
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Inherited
annotation class Isolated