package kingmc.common.context.beans.depends

/**
 * A descriptor describe dependencies that a bean is require
 *
 * @since 0.0.1
 * @author kingsthere
 */
@JvmInline
value class DependencyDescriptor(val beans: Set<String>)