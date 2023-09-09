package kingmc.common.context.delegate

import kingmc.common.context.Context
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * A property delegation populate required bean from context and
 * return to the delegated property
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface AutowiredDelegates<TBean : Any?>: ReadOnlyProperty<Any?, TBean> {

}