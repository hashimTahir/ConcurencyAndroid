/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.advancedthreadpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hashim.concurencyandroid.databinding.ActivityAdvancedThreadPoolExecutorBinding
import timber.log.Timber
import java.util.concurrent.Future


class AdvancedThreadPoolExecutorActivity : AppCompatActivity() {
    lateinit var hAdvancedThreadPoolExecutorBinding: ActivityAdvancedThreadPoolExecutorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hAdvancedThreadPoolExecutorBinding = ActivityAdvancedThreadPoolExecutorBinding.inflate(
            layoutInflater
        )
        setContentView(hAdvancedThreadPoolExecutorBinding.root)
        hRunHeavyBackgroudTask()
        hRunLightBackgroudTask()
        hRunTaskOnMainThread()
        hRunHeavyBackgroudTaskWithFutureSoItCanBeCancelled()
        /*Start the higest priroity task at end but finishes earlier*/
        hRunTaskWithHigestPriority()

    }

    private fun hRunHeavyBackgroudTask() {
        DefaultExecutorSupplier.hGetHeavyExecutor().execute {
            hExecuteTaskPrint(
                hTaskName = "hRunHeavyBackgroudTask",
                hThread = Thread.currentThread(),
                hSleepDuration = 4000,
            )
        }

    }

    private fun hRunHeavyBackgroudTaskWithFutureSoItCanBeCancelled() {
        val hFuture = DefaultExecutorSupplier.hGetLightExecutor().submit {
            hExecuteTaskPrint(
                hTaskName = "hRunHeavyBackgroudTaskWithFutureSoItCanBeCancelled",
                hThread = Thread.currentThread(),
                hSleepDuration = 1000,
            )
        }
        hCancelTask(hFuture = hFuture)

    }

    private fun hRunLightBackgroudTask() {
        DefaultExecutorSupplier.hGetLightExecutor().execute {
            hExecuteTaskPrint(
                hTaskName = "hRunLightBackgroudTask",
                hThread = Thread.currentThread(),
                hSleepDuration = 2000,
            )
        }
    }

    private fun hRunTaskOnMainThread() {
        DefaultExecutorSupplier.hGetMainThreadExecutor().execute {
            hExecuteTaskPrint(
                hTaskName = "hRunTaskOnMainThread",
                hThread = Thread.currentThread(),
                hSleepDuration = 500,
            )
        }
    }

    fun hCancelTask(hFuture: Future<*>) {
        hFuture.cancel(true)
    }

    fun hRunTaskWithHigestPriority() {
        DefaultExecutorSupplier.hGetHeavyExecutor()
            .submit(
                object : PriorityRunnable(PriorityTask.HIGH) {
                    override fun run() {
                        hExecuteTaskPrint(
                            hTaskName = "hRunTaskWithHigestPriority",
                            hThread = Thread.currentThread(),
                            hSleepDuration = 2000,
                        )
                    }
                }
            )
    }

    fun hExecuteTaskPrint(hTaskName: String, hThread: Thread, hSleepDuration: Long) {
        Timber.d("$hTaskName Started")
        Timber.d("$hTaskName with thread name ${hThread.name}")
        Thread.sleep(hSleepDuration)
        Timber.d("$hTaskName finished")
    }
}