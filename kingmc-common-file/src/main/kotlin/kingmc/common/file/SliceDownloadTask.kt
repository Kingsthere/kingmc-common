package kingmc.common.file

/**
 * Represents a `DownloadTask` that downloads file that split in multi slices
 *
 * @author kingsthere
 * @since 0.1.2
 */
interface SliceDownloadTask : DownloadTask {
    /**
     * Returns the count of slice to download
     */
    val sliceCount: Int
}