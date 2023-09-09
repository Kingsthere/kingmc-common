package kingmc.util.resource

import java.io.InputStream
import java.net.URL

open class URLResource(val url: URL) : Resource {
    /**
     * Gets this resource as a [URL] instance
     */
    override fun asURL(): URL {
        return url
    }

    /**
     * Gets this resource as a [InputStream] instance
     */
    override fun asInputStream(): InputStream {
        return url.openStream()
    }
}