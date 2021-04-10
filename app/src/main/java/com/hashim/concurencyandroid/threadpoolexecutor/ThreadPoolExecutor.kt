/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.threadpoolexecutor

import android.os.Process
import timber.log.Timber
import java.util.concurrent.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor

object ThreadPoolExecutor {
    var H_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    var H_KEEP_ALIVE_TIME = 1.toLong()
    var H_KEEP_ALIVE_TIME_UNIT: TimeUnit = TimeUnit.SECONDS
    const val H_THREAD_TAG = "hTag"


    var hTaskQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    val hEexecutorService: ExecutorService = ThreadPoolExecutor(
        H_NUMBER_OF_CORES,
        H_NUMBER_OF_CORES * 2,
        H_KEEP_ALIVE_TIME,
        H_KEEP_ALIVE_TIME_UNIT,
        hTaskQueue,
        BackgroundThreadFactory()
    )


    fun hGetService() = hEexecutorService
    fun hGetTaskQueue() = hTaskQueue

    private class BackgroundThreadFactory : ThreadFactory {
        override fun newThread(runnable: Runnable?): Thread {
            val hThread = Thread(runnable)
            hThread.name = "$H_THREAD_TAG ${hThread.name}"
            hThread.priority = Process.THREAD_PRIORITY_BACKGROUND

            hThread.setUncaughtExceptionHandler { thread, exception ->
                Timber.d(
                    "${thread.getName()}  encountered an error: ${exception.message} "
                )
            }
            return hThread
        }

    }
}