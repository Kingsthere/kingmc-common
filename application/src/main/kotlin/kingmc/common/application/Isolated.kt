package kingmc.common.application

import java.lang.annotation.Inherited

/**
 * Marker annotation for every instances that separated by applications, when the
 * application of instances disposed, instances will dispose automatically
 *
 * @since 0.0.7
 * @author kingsthere
 * @see Domain
 */
@Retention
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Inherited
annotation class Isolated