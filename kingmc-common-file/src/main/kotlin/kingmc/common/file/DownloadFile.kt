package kingmc.common.file

import java.io.Closeable
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.concurrent.atomic.AtomicLong

/**
 * Utility class to write downloaded bytes into given file
 *
 * @author kingsthere
 * @since 0.1.2
 */
class DownloadFile(file: File, fileSize: Long) : Closeable {
    private val randomAccessFile: RandomAccessFile
    private val channel: FileChannel // Thread safe channel
    private val wroteSize: AtomicLong = AtomicLong(0) // Length already written

    init {
        randomAccessFile = RandomAccessFile(file, "rw")
        randomAccessFile.setLength(fileSize)
        channel = randomAccessFile.channel
    }

    /**
     * Write data into file
     */
    @Throws(IOException::class)
    fun write(offset: Long, buffer: ByteBuffer) {
        buffer.flip()
        val length = buffer.limit()
        while (buffer.hasRemaining()) {
            channel.write(buffer, offset)
        }
        wroteSize.addAndGet(length.toLong())
    }

    /**
     * Get the size of bytes already written
     */
    fun getWroteSize(): Long {
        return wroteSize.get()
    }

    /**
     * Set the size of bytes already written
     */
    fun setWroteSize(wroteSize: Long) {
        this.wroteSize.set(wroteSize)
    }

    /**
     * Close
     */
    override fun close() {
        try {
            randomAccessFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}