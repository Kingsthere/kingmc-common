package kingmc.common.environment.maven.model

import kingmc.common.environment.AbstractXmlParser
import kingmc.common.environment.maven.DependencyScope
import kingmc.util.format.FormatContext
import kingmc.util.format.Formatted
import kingmc.util.format.formatWithContext
import org.w3c.dom.Element

/**
 * Represents a dependency that needs to be downloaded and inject into the classpath
 *
 * @since 0.0.6
 * @author kingsthere
 */
class Dependency : AbstractXmlParser {
    val groupId: String
    val artifactId: String
    val version: String
    val scope: DependencyScope

    constructor(groupId: String, artifactId: String, version: String, scope: DependencyScope) {
        this.groupId = groupId.replace("!", "")
        this.artifactId = artifactId
        this.version = version
        this.scope = scope
    }

    constructor(node: Element) : this(
        groupId = find("groupId", node),
        artifactId = find("artifactId", node),
        version = find("version", node, "RELEASE"),
        scope = DependencyScope.valueOf(find("scope", node, "runtime").uppercase())
    )

    override fun toString(): String {
        return "$groupId:$artifactId:$version"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dependency) return false

        if (groupId != other.groupId) return false
        if (artifactId != other.artifactId) return false
        if (version != other.version) return false
        return scope == other.scope
    }

    override fun hashCode(): Int {
        var result = groupId.hashCode()
        result = 31 * result + artifactId.hashCode()
        result = 31 * result + version.hashCode()
        result = 31 * result + scope.hashCode()
        return result
    }
}

@Formatted
fun dependency(groupId: String, artifactId: String, version: String, scope: DependencyScope, formatContext: FormatContext) =
    Dependency(
        groupId = formatContext.formatWithContext(groupId),
        artifactId = formatContext.formatWithContext(artifactId),
        version = formatContext.formatWithContext(version),
        scope = scope
    )