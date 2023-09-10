package kingmc.util.resource

import java.io.IOException
import java.io.InputStream
import java.net.URL

open class ClassLoaderResource(val classLoader: ClassLoader, val name: String) :
    URLResource(classLoader.getResource(name) ?: throw IOException("Unable to find resource $name")) {

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