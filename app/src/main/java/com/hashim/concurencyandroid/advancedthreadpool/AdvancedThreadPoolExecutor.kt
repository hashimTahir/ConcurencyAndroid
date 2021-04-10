/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.advancedthreadpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hashim.concurencyandroid.databinding.ActivityAdvancedThreadPoolExecutorBinding
import timber.log.Timber
import java.util.concurrent.Future

class AdvancedThreadPoolExecutor : AppCompatActivity() {
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

    }

    private fun hRunHeavyBackgroudTask() {
        DefaultExecutorSupplier.hGetHeavyExecutor().execute {
            Timber.d("Thread name ${Thread.currentThread().name}")
            Thread.sleep(4000)
            Timber.d("Heavy Task finished")
        }

    }

    private fun hRunHeavyBackgroudTaskWithFutureSoItCanBeCancelled() {
        val hFuture = DefaultExecutorSupplier.hGetHeavyExecutor().submit {
            Timber.d("Thread name ${Thread.currentThread().name}")
            Thread.sleep(4000)
            Timber.d("hRunHeavyBackgroudTaskWithFutureSoItCanBeCancelled Task finished")
        }
        hCancelTask(hFuture = hFuture)

    }

    private fun hRunLightBackgroudTask() {
        DefaultExecutorSupplier.hGetLightExecutor().execute {
            Timber.d("Thread name ${Thread.currentThread().name}")
            Thread.sleep(2000)
            Timber.d("Light Task finished")
        }
    }

    private fun hRunTaskOnMainThread() {
        DefaultExecutorSupplier.hGetMainThreadExecutor().execute {
            Timber.d("Thread name ${Thread.currentThread().name}")
            Thread.sleep(500)
            Timber.d("Main Task finished")
        }
    }

    fun hCancelTask(hFuture: Future<*>) {
        hFuture.cancel(true)
    }
}