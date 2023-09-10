package kingmc.util.resource

import java.io.File
import java.io.InputStream
import java.net.URL

open class FileResource(val file: File) : URLResource(file.toURI().toURL()) {

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