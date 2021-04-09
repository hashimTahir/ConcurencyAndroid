package com.hashim.concurencyandroid

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import timber.log.Timber
import timber.log.Timber.DebugTree


class BaseApplication : Application() {
    private var hMainThreadHanddler: Handler? = null


    fun hGetMainThreadHanddler(): Handler? {
        return hMainThreadHanddler
    }

    override fun onCreate() {
        super.onCreate()
        hInitTimber()
        hMainThreadHanddler = HandlerCompat.createAsync(Looper.getMainLooper())
    }

    private fun hInitTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, String.format(hTag, tag), message, t)
                }
            })
        }
    }

    companion object {
        const val hTag = "hashimTimberTags %s"
    }
}
