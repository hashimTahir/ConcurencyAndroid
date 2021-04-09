package com.hashim.concurencyandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hashim.concurencyandroid.ExecutorService.hGetExecutorService
import com.hashim.concurencyandroid.databinding.ActivityExecutorServiceBinding
import timber.log.Timber
import java.util.concurrent.Future


class ExecutorServiceActivity : AppCompatActivity() {
    lateinit var hActivityExecutorServiceBinding: ActivityExecutorServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityExecutorServiceBinding = ActivityExecutorServiceBinding.inflate(layoutInflater)
        setContentView(hActivityExecutorServiceBinding.getRoot())

        hActivityExecutorServiceBinding.hGetThreadNameB.setOnClickListener { v ->
            hUpdateUi()

            hTaskRunner()

            hUpdateUi()

            val hCallable = hTaskRunnerWithCallable()

            Timber.d(hCallable?.get().toString())

            hUpdateUi()

            hPrintThreadNameToConsole()
        }
    }

    /*Update the ui with the main handler */
    private fun hUpdateUi() {
        hGetBaseAppContext().hGetMainThreadHanddler()?.post {
            hPrintThreadNameToView()

        }
    }

    private fun hGetBaseAppContext(): BaseApplication {
        return application as BaseApplication
    }

    fun hTaskRunner() {
        hGetExecutorService().execute {
            Timber.d("hTaskRunner ..")
            hPrintThreadNameToConsole()
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
            }
        }
    }

    /*
    * Can be used to retrieve the result from the callable by calling future.get()
    * or cancel the task by calling future.cancel(boolean mayInterruptIfRunning)
    * */
    fun hTaskRunnerWithCallable(): Future<*>? {
        return hGetExecutorService().submit {
            Timber.d("hTaskRunnerWithCallable started ..")
            hPrintThreadNameToConsole()
            Thread.sleep(4000)
        }
    }

    fun hPrintThreadNameToConsole() {
        Timber.d("Current Thread is ${Thread.currentThread().name}")
    }

    /*Crashs if the Backgroud thread is called on button click because it tries to update the main thread*/
    fun hPrintThreadNameToView() {
        val text: String = hActivityExecutorServiceBinding.hThreadTv.getText().toString()
        hActivityExecutorServiceBinding.hThreadTv.text = " $text  ${Thread.currentThread().name}"
    }
}