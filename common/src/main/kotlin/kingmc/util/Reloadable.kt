package kingmc.util

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap

/**
 * A marker interface to mark the classes that is [Reloadable]
 *
 * @author kingsthere
 * @since 0.0.1
 */
interface Reloadable {
    /**
     * Reload
     */
    fun reload()
}

/**
 * A manager responsible for handling [Reloadable], `ReloadableManager` differentiate
 * [Reloadable]s by a [ReloadableScope], so it can reload multiple [Reloadable]s at the same time
 *
 * @author kingsthere
 * @since 0.1.1
 */
class ReloadableManager {
    private val _registeredReloadable: Multimap<ReloadableScope, Reloadable> = ArrayListMultimap.create(4, 8)

    /**
     * Register the given [Reloadable] into this reloadable manager
     *
     * @param reloadable reloadable to register into this reloadable manager
     * @param scope the scope to the registering [Reloadable]
     */
    fun register(reloadable: Reloadable, scope: ReloadableScope = ReloadableScope.Default) {
        _registeredReloadable.put(scope, reloadable)
    }

    /**
     * Reload all [Reloadable]s that are registered in this `ReloadableManager` with the given scope
     *
     * @param scope the `ReloadableScope` instance to determine [Reloadable]s to reload
     */
    fun reload(scope: ReloadableScope) {
        _registeredReloadable.get(scope).forEach(Reloadable::reload)
    }

    /**
     * Reload all [Reloadable]s that are registered in this reloadable manager
     */
    fun reloadAll() {
        _registeredReloadable.values().forEach(Reloadable::reload)
    }
}

/**
 * A `ReloadableScope` is an identifier used for differentiate [Reloadable]s
 *
 * @author kingsthere
 * @since 0.1.1
 */
interface ReloadableScope {
    /**
     * A general `ReloadableScope` implementation represents the default reloadable scope
     */
    object Default : ReloadableScope
}