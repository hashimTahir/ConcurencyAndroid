/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.threadpoolexecutor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hashim.concurencyandroid.BaseApplication
import com.hashim.concurencyandroid.databinding.ActivityThreadPoolBinding
import timber.log.Timber
import java.util.concurrent.Callable
import java.util.concurrent.Future

class ThreadPoolActivity : AppCompatActivity() {
    lateinit var hActivityThreadPoolBinding: ActivityThreadPoolBinding


    val hRuningTaskList = mutableListOf<Future<*>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityThreadPoolBinding = ActivityThreadPoolBinding.inflate(layoutInflater)
        setContentView(hActivityThreadPoolBinding.root)

        hActivityThreadPoolBinding.hRunThreadPoolExecutor.setOnClickListener {

            for (i in 1..50) {
                hAddCallable(hCreateCallable())
            }

            /*This freezes the ui as it waits 100*/
//
//            for (future in hRuningTaskList) {
//                Timber.d("Future ${future.get()}")
//            }
            hHandlerToPostResults()
        }
    }

    fun hHandlerToPostResults() {
        (application as BaseApplication).hGetMainThreadHanddler()?.post {
            hRuningTaskList.forEachIndexed { index, future ->
                Timber.d("Future ${future.get()}")
                hPrintThreadNameToView()
                if (index == 8) {
                    hCancelAllTasks()
                }
            }

        }
    }

    fun hAddCallable(callable: Callable<*>?) {
        val future: Future<*> = ThreadPoolExecutor.hEexecutorService.submit(callable)
        hRuningTaskList.add(future)
    }

    /* Remove all tasks in the queue and stop all running threads
 * Notify UI thread about the cancellation
 */
    fun hCancelAllTasks() {
        synchronized(this) {
            ThreadPoolExecutor.hTaskQueue.clear()
            for (task in hRuningTaskList) {
                if (!task.isDone()) {
                    task.cancel(true)
                }
            }
            hRuningTaskList.clear()
        }

    }

    fun hCreateCallable(): Callable<String> {
        val hCallable = object : Callable<String> {
            override fun call(): String {
                Thread.sleep(1000)
                return Thread.currentThread().name
            }

        }
        return hCallable
    }

    fun hPrintThreadNameToView() {
        val text: String = hActivityThreadPoolBinding.hThreadNameTv.getText().toString()
        hActivityThreadPoolBinding.hThreadNameTv.text = " $text  ${Thread.currentThread().name}"
    }
}