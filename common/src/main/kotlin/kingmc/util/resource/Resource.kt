package kingmc.util.resource

import java.io.InputStream
import java.net.URL

/**
 * An interface for declaring a resource in kingmc framework
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface Resource {
    /**
     * Gets this resource as a [URL] instance
     */
    fun asURL(): URL

    /**
     * Gets this resource as a [InputStream] instance
     */
    fun asInputStream(): InputStream
}