package kingmc.util.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * A class responsible for providing [Gson] instances to serialize/deserialize
 * json texts
 *
 * @since 0.0.1
 * @author kingsthere
 */
object Gsons : GsonProvider {
    /**
     * Standard [Gson] instance without any properties
     *
     * @since 0.0.1
     */
    val standard: Gson by lazy {
        return@lazy GsonBuilder()
            .create()
    }
}