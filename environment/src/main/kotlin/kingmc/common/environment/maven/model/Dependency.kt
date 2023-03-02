package kingmc.common.environment.maven.model

import kingmc.common.environment.AbstractXmlParser
import kingmc.common.environment.maven.DependencyScope
import org.w3c.dom.Element

/**
 * Represents a dependency that needs to be downloaded and inject into the classpath
 *
 * @since 0.0.6
 * @author kingsthere
 */
data class Dependency(val groupId: String, val artifactId: String, val version: String, val scope: DependencyScope): AbstractXmlParser() {
    constructor(node: Element) : this(
        groupId = find("groupId", node),
        artifactId = find("artifactId", node),
        version = find("version", node),
        scope = DependencyScope.valueOf(find("scope", node, "runtime").uppercase())
    )

    override fun toString(): String {
        return "$groupId:$artifactId:$version"
    }
}