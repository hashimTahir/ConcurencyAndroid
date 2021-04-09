package com.hashim.androidjavaconcurency

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hashim.androidjavaconcurency.ExecutorService.hGetExecutorService
import com.hashim.concurencyandroid.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    lateinit var hActivityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(hActivityMainBinding.getRoot())

        Timber.d("Starting")
        hActivityMainBinding.hGetThreadNameB.setOnClickListener { v ->
            hPrintThreadNameToConsole()
            hPrintThreadNameToView()
            hRunOnBackgroudThread()
        }
    }

    /*Update the ui with the main handler */
    private fun hUpdateUi() {
        hGetBaseAppContext().hGetMainThreadHanddler()?.post {
            hPrintThreadNameToConsole()
            hPrintThreadNameToView()
        }
    }

    private fun hGetBaseAppContext(): BaseApplication {
        return application as BaseApplication
    }

    fun hRunOnBackgroudThread() {
        hGetExecutorService().execute {
            Timber.d("Long running task is started ..")
            hPrintThreadNameToConsole()
            try {
                Thread.sleep(3000)
                hUpdateUi()
            } catch (e: InterruptedException) {
            }
        }
    }

    fun hPrintThreadNameToConsole() {
        Timber.d("Current Thread is ${Thread.currentThread().name}")
    }

    /*Crashs if the Backgroud thread is called on button click because it tries to update the main thread*/
    fun hPrintThreadNameToView() {
        val text: String = hActivityMainBinding.hThreadTv.getText().toString()
        hActivityMainBinding.hThreadTv.text = " $text  ${Thread.currentThread().name}"
    }
}