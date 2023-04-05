package kingmc.common.context.resource

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.full.isSubclassOf

/**
 * Base class for declaring resources, for example:
 * ```
 * class Config : Resource(source = ResourceSource.JAR, value = "config.yml")
 * ```
 *
 * @param value the value to get the config from `ResourceSource`
 * @param outPath the path to save the files got from `ResourceSource`
 * @since 0.0.5
 * @author kingsthere
 */
open class Resource(val source: ResourceSource<*>, val value: Any) {
    init {
        // Check if the `value` is compliance for the type of the source
        if (!value::class.isSubclassOf(source.valueClass)) {
            throw IllegalArgumentException("Value $value is not compliance for the type of the source (${source.valueClass})")
        }
    }

    /**
     * Gets this resource as a file
     *
     * @param classLoader the class loader to load resources if the value
     *        of the [source] needed to load from a jar
     * @return the file got
     */
    fun getFile(outPath: Path, classLoader: ClassLoader?): File {
        val resourceOut = outPath.toFile()
        if (resourceOut.exists()) {
            return resourceOut
        }
        when (source) {
            ResourceSource.JAR -> {
                checkNotNull(classLoader)
                try {
                    val resourceIn = classLoader.getResource(value as String) ?: throw ResourceLoadException("Unable to get resource from $value", null)
                    resourceIn.openStream().use { input ->
                        if (!resourceOut.parentFile.exists()) {
                            resourceOut.parentFile.mkdirs()
                        }
                        Files.copy(input, resourceOut.toPath())
                    }
                } catch (e: IOException) {
                    throw ResourceLoadException("Unable to load resource from $value to $outPath", e)
                } catch (e: MalformedURLException) {
                    throw ResourceLoadException("Unable to load resource from $value to $outPath", e)
                }
            }
            ResourceSource.URL -> {
                try {
                    val resourceIn = value as URL
                    resourceIn.openStream().use { input ->
                        if (!resourceOut.parentFile.exists()) {
                            resourceOut.parentFile.mkdirs()
                        }
                        Files.copy(input, resourceOut.toPath())
                    }
                } catch (e: IOException) {
                    throw ResourceLoadException("Unable to load resource from $value to $outPath", e)
                } catch (e: MalformedURLException) {
                    throw ResourceLoadException("Unable to load resource from $value to $outPath", e)
                }
            }
        }
        return resourceOut
    }

    /**
     * Gets this resource as a input stream
     *
     * @return the file got
     */
    fun getInputStream(classLoader: ClassLoader?): InputStream {
        when (source) {
            ResourceSource.JAR -> {
                checkNotNull(classLoader)
                try {
                    val resourceIn = classLoader.getResource(value as String) ?: throw ResourceLoadException("Unable to get resource from $value", null)
                    return resourceIn.openStream()
                } catch (e: IOException) {
                    throw ResourceLoadException("Unable to open resource input stream for $value", e)
                } catch (e: MalformedURLException) {
                    throw ResourceLoadException("Unable to open resource input stream for $value", e)
                }
            }
            ResourceSource.URL -> {
                try {
                    val resourceIn = value as URL
                    return resourceIn.openStream()
                } catch (e: IOException) {
                    throw ResourceLoadException("Unable to open resource input stream for $value", e)
                } catch (e: MalformedURLException) {
                    throw ResourceLoadException("Unable to open resource input stream for $value", e)
                }
            }
             else -> throw IllegalArgumentException("Unsupported source of resource")
        }
    }
}

fun Resource(value: Any, source: ResourceSource<*> = ResourceSource.JAR): Resource {
    return Resource(source, value)
}