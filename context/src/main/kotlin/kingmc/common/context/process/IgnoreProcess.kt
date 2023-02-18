package kingmc.common.context.process

import java.lang.annotation.Inherited

/**
 * Add this annotation to a bean if you don't want **any [BeanProcessor]** to process this bean
 *
 * @since 0.0.5
 * @author kingsthere
 */
@Retention
@Target
@Inherited
annotation class IgnoreProcess
