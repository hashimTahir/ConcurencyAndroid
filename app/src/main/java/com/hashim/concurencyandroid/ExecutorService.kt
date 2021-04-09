package com.hashim.androidjavaconcurency

import java.util.concurrent.Executors

object ExecutorService {
    private val hExecutorService = Executors.newFixedThreadPool(4)

    fun hGetExecutorService() = hExecutorService
}