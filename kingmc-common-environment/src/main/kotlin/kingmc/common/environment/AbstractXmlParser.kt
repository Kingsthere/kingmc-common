package kingmc.common.environment

import org.w3c.dom.Element
import java.text.ParseException
import java.util.regex.Pattern

/**
 * Base class for any class that needs to do XML parsing
 *
 * @author Zach Deibert, sky
 * @since 1.0.0
 */
abstract class AbstractXmlParser {
    companion object {

        /**
         * The pattern to use to detect when a variable should be substituted in the
         * pom
         *
         * @since 1.0.0
         */
        @JvmStatic
        private val SUBSTITUTION_PATTERN = Pattern.compile("\\$\\{([^}]+)}")

        /**
         * Gets the replacement value for a substitution variable
         *
         * @param key The key of the variable
         * @param pom The pom document
         * @return The value that it should be replaced with
         * @throws ParseException If the variable could not be resolved
         * @since 1.0.0
         */
        @JvmStatic
        @Throws(ParseException::class)
        private fun getReplacement(key: String, pom: Element): String {
            return if (key.startsWith("project.")) {
                find(key.substring("project.".length), pom)
            } else if (key.startsWith("pom.")) {
                find(key.substring("pom.".length), pom)
            } else {
                throw ParseException(String.format("Unknown variable '%s'", key), -1)
            }
        }

        /**
         * Replaces all the variables in a string of text
         *
         * @param text The text to replace the variables in
         * @param pom  The pom document
         * @return The text with all the variables replaced
         * @throws ParseException If the variable could not be resolved
         * @since 1.0.0
         */
        @JvmStatic
        @Throws(ParseException::class)
        private fun replaceVariables(text: String, pom: Element): String {
            var text = text
            val matcher = SUBSTITUTION_PATTERN.matcher(text)
            while (matcher.find()) {
                text = matcher.replaceFirst(getReplacement(matcher.group(1), pom))
            }
            return text
        }

        /**
         * Searches for a node and returns the text inside of it
         *
         * @param name The name of the node to search for
         * @param node The node to search inside of
         * @param def  The default value, or `null` if the value is
         * required
         * @return The text content of the node it found, or `def` if the
         * node is not found
         * @throws ParseException If the node cannot be found and there is no default value
         * @since 1.0.0
         */
        @JvmStatic
        @Throws(ParseException::class)
        protected fun find(name: String, node: Element, def: String? = null): String {
            var list = node.childNodes
            for (i in 0 until list.length) {
                val n = list.item(i)
                if (n.nodeName == name) {
                    return try {
                        replaceVariables(n.textContent, node.ownerDocument.documentElement)
                    } catch (ex: ParseException) {
                        def ?: throw ex
                    }
                }
            }
            list = node.getElementsByTagName(name)
            return if (list.length > 0) {
                try {
                    replaceVariables(list.item(0).textContent, node.ownerDocument.documentElement)
                } catch (ex: ParseException) {
                    def ?: throw ex
                }
            } else def ?: throw ParseException(String.format("Unable to find required tag '%s' in node", name), -1)
        }
    }
}
