package kingmc.common.environment

import kingmc.common.logging.Logger
import kingmc.util.AppendableURLClassLoader
import sun.misc.Unsafe
import java.io.File
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.reflect.Field
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path

/**
 * A utility class to appending urls into classpath
 */
class ClassAppender(
    val classLoader: ClassLoader,
    val logger: Logger
) {
    var lookup: MethodHandles.Lookup? = null
    var unsafe: Unsafe? = null

    init {
        try {
            val field = Unsafe::class.java.getDeclaredField("theUnsafe")
            field.isAccessible = true
            unsafe = field[null] as Unsafe
            val lookupField = MethodHandles.Lookup::class.java.getDeclaredField("IMPL_LOOKUP")
            val lookupBase = unsafe!!.staticFieldBase(lookupField)
            val lookupOffset = unsafe!!.staticFieldOffset(lookupField)
            lookup = unsafe!!.getObject(lookupBase, lookupOffset) as MethodHandles.Lookup
        } catch (ignore: Throwable) {  }
    }

    fun addPath(path: Path) {
        try {
            val file = File(path.toUri().path)
            val loader = classLoader
            // For AppendableURLClassLoader
            if (classLoader is AppendableURLClassLoader) {
                classLoader.addPath(path)
                return
            }

            // Other class loaders
            when (loader.javaClass.name) {
                "jdk.internal.loader.ClassLoaders\$AppClassLoader" -> {
                    addURL(loader, ucp(loader.javaClass), file)
                }
                "net.minecraft.launchwrapper.LaunchClassLoader" -> {
                    val methodHandle = lookup!!.findVirtual(
                        URLClassLoader::class.java,
                        "addURL",
                        MethodType.methodType(Void.TYPE, URL::class.java)
                    )
                    methodHandle.invoke(loader, file.toURI().toURL())
                }
                else -> {
                    val ucpField: Field? = try {
                        URLClassLoader::class.java.getDeclaredField("ucp")
                    } catch (e1: NoSuchFieldError) {
                        ucp(loader.javaClass)
                    } catch (e1: NoSuchFieldException) {
                        ucp(loader.javaClass)
                    }
                    addURL(loader, ucpField, file)
                }
            }
        } catch (t: Throwable) {
            logger.logError("An error occurred while trying to append path into classloader", t)
        }
    }

    fun isExists(path: String?): Boolean {
        return try {
            Class.forName(path, false, classLoader)
            true
        } catch (ignored: ClassNotFoundException) {
            false
        }
    }

    @Throws(Throwable::class)
    private fun addURL(loader: ClassLoader, ucpField: Field?, file: File) {
        checkNotNull(ucpField) { "ucp field not found" }
        val ucp = unsafe!!.getObject(loader, unsafe!!.objectFieldOffset(ucpField))
        try {
            val methodHandle =
                lookup!!.findVirtual(ucp.javaClass, "addURL", MethodType.methodType(Void.TYPE, URL::class.java))
            methodHandle.invoke(ucp, file.toURI().toURL())
        } catch (e: NoSuchMethodError) {
            throw IllegalStateException(
                "Unsupported (classloader: " + loader.javaClass.name + ", ucp: " + ucp.javaClass.name + ")",
                e
            )
        }
    }

    private fun ucp(loader: Class<*>): Field? {
        return try {
            loader.getDeclaredField("ucp")
        } catch (e2: NoSuchFieldError) {
            val superclass = loader.superclass
            if (superclass == Any::class.java) {
                null
            } else ucp(superclass)
        } catch (e2: NoSuchFieldException) {
            val superclass = loader.superclass
            if (superclass == Any::class.java) {
                null
            } else ucp(superclass)
        }
    }
}