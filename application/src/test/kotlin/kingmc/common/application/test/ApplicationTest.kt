package kingmc.common.application.test

import kingmc.common.application.*
import kingmc.common.context.Context
import org.junit.jupiter.api.Test

class ApplicationTest {
    @Test
    fun testApplication() {
        val application = object : Application<Context> {
            /**
             * The [TContext] that loading this application
             */
            override val context: Context
                get() = TODO("Not yet implemented")

            /**
             * The environment of this application
             */
            override val environment: ApplicationEnvironment
                get() = TODO("Not yet implemented")

            override val name: String
                get() = TODO("Not yet implemented")

            /**
             * Called to dispose/terminate this application
             */
            override fun dispose() {
                TODO("Not yet implemented")
            }

        }

        application {
            applicationOperation()
        }
    }

}

fun applicationOperation() {
    println(currentApplication())
}