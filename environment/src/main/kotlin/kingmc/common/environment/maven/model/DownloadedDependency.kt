package kingmc.common.environment.maven.model

import java.io.File

/**
 * A data class packaged a downloaded dependencies
 *
 * @since 0.0.6
 * @author kingsthere
 */
data class DownloadedDependency(val jar: File, val jarSha1: File, val pom: File, val pomSha1: File, val transitive: List<DownloadedDependency>)