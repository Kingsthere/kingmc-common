package kingmc.util.annotation.cglib

import net.sf.cglib.core.NamingPolicy
import net.sf.cglib.core.Predicate

/**
 * The default name policy to generate proxied annotations
 *
 * @author kingsthere
 * @since 0.0.7
 */
object AnnotationNamingPolicy : NamingPolicy {
    override fun getClassName(prefix: String?, source: String, key: Any, names: Predicate): String {
        var processedPrefix = prefix
        if (processedPrefix == null) {
            processedPrefix = "net.sf.cglib.empty.Object"
        } else if (processedPrefix.startsWith("java")) {
            processedPrefix = "$$processedPrefix"
        }
        val base = processedPrefix + "$$" +
                source.substring(source.lastIndexOf('.') + 1) +
                tag + "$$" +
                Integer.toHexString(if (STRESS_HASH_CODE) 0 else key.hashCode())
        var attempt = base
        var index = 2
        while (names.evaluate(attempt)) attempt = base + "_" + index++
        return attempt
    }

    /**
     * Returns a string which is incorporated into every generated class name.
     * By default, returns "ByCGLIB"
     */
    private val tag: String
        get() = "ByKingMC"

    override fun hashCode(): Int {
        return tag.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is AnnotationNamingPolicy && tag == tag
    }

        /**
         * This allows testing collisions of `key.hashCode()`.
         */
    private val STRESS_HASH_CODE = java.lang.Boolean.getBoolean("net.sf.cglib.test.stressHashCodes")
}