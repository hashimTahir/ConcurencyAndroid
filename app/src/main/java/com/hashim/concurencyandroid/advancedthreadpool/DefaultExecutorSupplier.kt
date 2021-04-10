/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.advancedthreadpool

import android.os.Process.THREAD_PRIORITY_BACKGROUND
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object DefaultExecutorSupplier {
    /*
    * Number of cores to decide the number of threads
    */
    private var H_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    /*
    * thread pool executor for background tasks
    */
    private val backgroundPriorityThreadFactory: ThreadFactory =
        PriorityThreadFactory(THREAD_PRIORITY_BACKGROUND)

    private val hHeavyThreadPoolExecutor by lazy {
        ThreadPoolExecutor(
            H_NUMBER_OF_CORES * 2,
            H_NUMBER_OF_CORES * 2,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            backgroundPriorityThreadFactory
        )
    }

    /*
    * thread pool executor for light weight background tasks
    */
    private val hLightThreadPoolExecutor by lazy {
        ThreadPoolExecutor(
            H_NUMBER_OF_CORES * 2,
            H_NUMBER_OF_CORES * 2,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            backgroundPriorityThreadFactory
        )
    }

    /*
    * thread pool executor for main thread tasks
    */
    private val hMainThreadExecutor by lazy { MainThreadExecutor() }


    fun hGetLightExecutor() = hLightThreadPoolExecutor

    fun hGetHeavyExecutor() = hHeavyThreadPoolExecutor

    fun hGetMainThreadExecutor() = hMainThreadExecutor
}