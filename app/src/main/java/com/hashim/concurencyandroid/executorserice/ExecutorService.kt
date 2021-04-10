/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.executorserice

import java.util.concurrent.Executors

object ExecutorService {
    const val H_THREAD_POOL_SIZE = 4

    /*
    *
    *
    * ExecutorService executorService = Executors.newCachedThreadPool();
    *
    * newCachedThreadPool creates a new thread when there is a task in the queue.
    * When there is no tasks in the queue for 60 seconds, the idle threads will be terminated.
    *
    *
    *
    * ExecutorService executorService = Executors.newSingleThreadExecutor();
    * */
    private val hExecutorService = Executors.newFixedThreadPool(H_THREAD_POOL_SIZE)


    fun hGetExecutorService() = hExecutorService
}