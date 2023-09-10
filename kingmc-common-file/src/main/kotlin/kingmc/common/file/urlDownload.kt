package kingmc.common.file

import java.io.File
import java.net.URL

/**
 * `FileDownloader` downloads file from url
 */
interface URLFileDownloader : FileDownloader {
    /**
     * Download the file from given url
     */
    fun download(source: URL, output: File): DownloadTask
}

/**
 * Represents a `DownloadTask` that downloads file from url
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface URLDownloadTask : DownloadTask {
    /**
     * The source url this download task downloads from
     */
    val source: URL
}