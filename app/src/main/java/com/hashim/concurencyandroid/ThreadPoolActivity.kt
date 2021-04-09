package com.hashim.concurencyandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

            for (future in hRuningTaskList) {
                Timber.d("Future ${future.get()}")
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
    fun cancelAllTasks() {
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
                return Thread.currentThread().name
            }

        }
        return hCallable
    }
}