package kingmc.common.file.concurrent

import kingmc.common.file.DownloadTask
import kingmc.common.file.URLFileDownloader
import java.io.File
import java.net.URL
import java.util.Collections
import java.util.LinkedList
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

/**
 * `URLFileDownloader` implementation for http urls
 *
 * @author kingsthere
 * @since 0.1.2
 */
class ExecutorHttpURLDownloader(
    val sliceCount: Int,
    val executor: ExecutorService = DownloadExecutors()
) : URLFileDownloader {
    val downloadTasks: MutableList<DownloadTask> = Collections.synchronizedList(LinkedList())

    /**
     * Download the file from given url
     */
    override fun download(source: URL, output: File): DownloadTask {
        return ExecutorHttpURLDownloadTask(executor, sliceCount, output, source) {
            downloadTasks.remove(it)
        }.also {
            downloadTasks.add(it)
        }
    }
}