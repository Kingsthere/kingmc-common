package kingmc.common.file.concurrent

import kingmc.common.file.DownloadFile
import kingmc.common.file.SliceDownloadTask
import kingmc.common.file.URLDownloadTask
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future


const val BUF_LEN: Int = 1024 * 1024

/**
 * An [URLDownloadTask] implemented downloading file by the given executor
 */
class ExecutorHttpURLDownloadTask(
    val executor: ExecutorService,
    override val sliceCount: Int,
    override val output: File,
    override val source: URL,
    val onFinish: (ExecutorHttpURLDownloadTask) -> Unit
) : URLDownloadTask, SliceDownloadTask {
    @Volatile
    private var _progress: Long = 0

    @Volatile
    private var _length: Long = 0

    @Synchronized
    override fun run() {
        _progress = 0

        val length = getFileSize()
        this._length = length

        // Calculate the size for each slice
        val sliceSize = length / sliceCount
        val downloadFile = DownloadFile(output, length)

        val tasks = mutableListOf<Future<*>>()
        repeat(sliceCount) { index ->
            // Calculate the start index
            var lowerbound = index * sliceSize

            // Calculate the end index
            val end = if (index == (sliceCount - 1)) {
                length
            } else {
                sliceSize * (index + 1) - 1
            }

            tasks.add(executor.submit {
                connect(source, lowerbound, end).use { sliceConnection ->
                    val buffer = ByteBuffer.allocate(BUF_LEN)

                    while (lowerbound <= end) {
                        buffer.clear()
                        val len = sliceConnection.read(buffer)
                        downloadFile.write(lowerbound, buffer)
                        if (len == -1) {
                            onFinish()
                            break
                        }

                        lowerbound += len
                        synchronized(this) {
                            _progress += len
                        }
                        if (_progress == _length) {
                            onFinish()
                            break
                        }
                    }
                }
            })
        }
        // Join all tasks
        tasks.forEach { it.get() }
    }

    @Throws(IOException::class)
    private fun connect(url: URL, start: Long, end: Long): ReadableByteChannel {
        val conn = url.openConnection() as HttpURLConnection
        conn.setRequestMethod("GET")
        conn.setRequestProperty("Range", "bytes=$start-$end")
        conn.connect()
        val statusCode = conn.getResponseCode()
        if (HttpURLConnection.HTTP_PARTIAL != statusCode) {
            conn.disconnect()
            throw IOException("Error status code：$statusCode")
        }
        return Channels.newChannel(conn.inputStream)
    }

    private fun getFileSize(): Long {
        return try {
            val conn = source.openConnection() as HttpURLConnection
            conn.setRequestMethod("HEAD")
            conn.connect()
            conn.contentLengthLong
        } catch (e: MalformedURLException) {
            throw RuntimeException("Malformed URL", e)
        } catch (e: IOException) {
            -1
        }
    }

    fun onFinish() {
        onFinish.invoke(this)
    }

    /**
     * Returns the current download progress (in percentage), between
     * `0.0` to `1.0`
     */
    override val progress: Float
        get() = _progress.toFloat() / _length.toFloat()
}