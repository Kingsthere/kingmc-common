package kingmc.common.file

import java.io.File

/**
 * An interface represents a file download task created by `FileDownloader`
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface DownloadTask : Runnable {
    /**
     * The output path this downloads task store downloaded content to
     */
    val output: File

    /**
     * Returns the current download progress (in percentage), between
     * `0.0` to `1.0`
     */
    val progress: Float
}