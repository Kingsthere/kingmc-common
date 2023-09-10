package kingmc.common.file.concurrent

import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DownloadExecutors : ThreadPoolExecutor(
    0,
    Int.MAX_VALUE,
    45,
    TimeUnit.SECONDS,
    SynchronousQueue(),
    ThreadFactory { runnable ->
        val result = Thread(runnable, "download executors")
        result.setDaemon(false)
        result
    },
    AbortPolicy()
) {
    init {
        allowCoreThreadTimeOut(true)
    }

    override fun execute(command: Runnable) {
        super.execute(command)
    }
}