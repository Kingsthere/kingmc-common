package kingmc.common.context.annotation

/**
 * Indicate that the elements annotated with [@Bean][Bean]
 * will be injected into the ioc container
 *
 *
 * You can inject beans into ioc container by
 * these ways: functions, property getter..., for example:
 * ```
 * // The owner class of function that registering beans
 * // must annotated with @Configuration
 * @Configuration
 * class BeanConfig {
 *     // The bean will get from the
 *     // returning value of this function
 *     // and inject into the ioc container
 *     @Bean
 *     fun beanA(): Bean {
 *         return // ...
 *     }
 *
 * }
 * ```
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Target(AnnotationTarget.FUNCTION)
@Retention
@MustBeDocumented
annotation class Bean(
    /**
     * The name of this bean
     *
     * @since 0.0.1
     */
    val name: String = ""
)
