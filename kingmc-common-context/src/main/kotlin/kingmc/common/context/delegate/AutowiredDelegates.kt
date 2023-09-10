package kingmc.common.context.delegate

import kotlin.properties.ReadOnlyProperty


/**
 * A property delegation populate required bean from context and
 * return to the delegated property
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface AutowiredDelegates<TBean : Any?> : ReadOnlyProperty<Any?, TBean> {

}