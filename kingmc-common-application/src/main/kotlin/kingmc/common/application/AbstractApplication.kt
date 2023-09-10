package kingmc.common.application

import kingmc.common.context.Context
import kingmc.util.ReloadableManager
import kingmc.util.format.FormatContext

/**
 * An abstract implementation of `Application`
 *
 * @author kingsthere
 * @since 0.1.2
 */
abstract class AbstractApplication(
    override val name: String,
    override val context: Context,
    override val environment: ApplicationEnvironment,
) : Application, FormatCapableApplication {
    /**
     * Shutdown hooks to be executed when application [shutdown]
     */
    private val shutdownHooks: MutableList<() -> Unit> = mutableListOf()
    override val applicationLocalMap: ApplicationLocalMap = ApplicationLocalMap()
    override val reloadableManager: ReloadableManager = ReloadableManager()

    override fun addShutdownHook(shutdownHook: () -> Unit) {
        this.shutdownHooks.add(shutdownHook)
    }

    override fun shutdown() {
        this.shutdownHooks.forEach { hook -> hook() }
        context.dispose()
    }

    private val propertiesFormatContext by lazy {
        FormatContext(this.properties)
    }

    private val _formatContext by lazy {
        propertiesFormatContext.with(context.getFormatContext())
    }

    /**
     * Get the format context that this holder holding
     */
    override fun getFormatContext(): FormatContext {
        return _formatContext
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractApplication

        if (context != other.context) return false
        if (environment != other.environment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }

    override fun toString(): String {
        return "AbstractApplication(name='$name', context=$context)"
    }

}