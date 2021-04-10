/*
 * Copyright (c) 2021/  4/ 10.  Created by Hashim Tahir
 */

package com.hashim.concurencyandroid.advancedthreadpool

import android.os.Process
import timber.log.Timber
import java.util.concurrent.ThreadFactory

class PriorityThreadFactory(private val hThreadPriority: Int) : ThreadFactory {
    override fun newThread(runnable: Runnable): Thread {
        val wrapperRunnable = Runnable {
            try {
                Process.setThreadPriority(hThreadPriority)
            } catch (t: Throwable) {
                Timber.e("Exception ${t.message}")
            }
            runnable.run()
        }
        return Thread(wrapperRunnable)
    }
}