package kingmc.common.application.test

import kingmc.common.application.Application
import kingmc.common.application.ApplicationEnvironment
import kingmc.common.application.ApplicationLocalMap
import kingmc.common.context.Context
import kingmc.util.ReloadableManager

class TestApplication : Application {
    override val context: Context
        get() = TODO("Not yet implemented")
    override val environment: ApplicationEnvironment
        get() = TODO("Not yet implemented")
    override val name: String
        get() = "test"
    override val applicationLocalMap: ApplicationLocalMap
        get() = TODO("Not yet implemented")
    override val reloadableManager: ReloadableManager
        get() = TODO("Not yet implemented")

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    override fun addShutdownHook(shutdownHook: () -> Unit) {
        TODO("Not yet implemented")
    }
}