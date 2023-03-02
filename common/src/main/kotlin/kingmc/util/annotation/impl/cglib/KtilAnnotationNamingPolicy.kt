package kingmc.util.annotation.impl.cglib

import net.sf.cglib.core.NamingPolicy
import net.sf.cglib.core.Predicate

/**
 * The default name policy to generate proxied annotations
 *
 * @since 0.0.7
 * @author kingsthere
 */
object KtilAnnotationNamingPolicy : NamingPolicy {
    override fun getClassName(prefix: String?, source: String, key: Any, names: Predicate): String {
        var prefix = prefix
        if (prefix == null) {
            prefix = "net.sf.cglib.empty.Object"
        } else if (prefix.startsWith("java")) {
            prefix = "$$prefix"
        }
        val base = prefix + "$$" +
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
        get() = "ByKtil"

    override fun hashCode(): Int {
        return tag.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is KtilAnnotationNamingPolicy && other.tag == tag
    }

        /**
         * This allows to test collisions of `key.hashCode()`.
         */
    private val STRESS_HASH_CODE = java.lang.Boolean.getBoolean("net.sf.cglib.test.stressHashCodes")
}