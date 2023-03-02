package kingmc.common.environment.maven.model

import kingmc.common.environment.AbstractXmlParser
import org.w3c.dom.Element

/**
 * Represents a maven repository to download [Dependency] from
 *
 * @since 0.0.6
 * @author kingsthere
 */
data class Repository(val url: String): AbstractXmlParser() {
    constructor(node: Element) : this(find("url", node))
    override fun toString(): String {
        return url
    }
}