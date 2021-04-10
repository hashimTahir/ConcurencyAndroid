/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.advancedthreadpool

import java.util.concurrent.*

/*
* Override ThreadPoolfactory
* overriede submit to pass priority runnable
* compare to cheeck the oridanl values of enum. and initiate excution based on it.
* */
class PriorityThreadPoolExecutor(
    corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long,
    unit: TimeUnit?, threadFactory: ThreadFactory?
) : ThreadPoolExecutor(
    corePoolSize,
    maximumPoolSize,
    keepAliveTime,
    unit,
    PriorityBlockingQueue(),
    threadFactory
) {
    override fun submit(task: Runnable): Future<*> {
        val futureTask = PriorityFutureTask(task as PriorityRunnable)
        execute(futureTask)
        return futureTask
    }

    private class PriorityFutureTask(private val priorityRunnable: PriorityRunnable) :
        FutureTask<PriorityRunnable?>(priorityRunnable, null),
        Comparable<PriorityFutureTask?> {

        override fun compareTo(other: PriorityFutureTask?): Int {
            val p1: PriorityTask = priorityRunnable.priority
            val p2: PriorityTask = other?.priorityRunnable?.priority!!
            return p2.ordinal - p1.ordinal
        }
    }
}