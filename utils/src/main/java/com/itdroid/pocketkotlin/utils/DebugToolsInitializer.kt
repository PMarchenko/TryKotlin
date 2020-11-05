package com.itdroid.pocketkotlin.utils

import android.content.Context
import android.os.StrictMode
import android.util.Log
import androidx.annotation.Keep
import androidx.startup.Initializer
import timber.log.Timber

/**
 * @author itdroid
 */
@Suppress("unused")
@Keep
class DebugToolsInitializer : Initializer<Boolean> {

    override fun create(context: Context): Boolean {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            Timber.plant(DebugLogcatTree())
        } else {
            Timber.plant(ProdLogcatTree(), CrashlyticsTree())
        }

        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

private class DebugLogcatTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        Log.println(priority, "PocketKotlin-$tag", message)
    }
}

private class ProdLogcatTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.ERROR || priority == Log.ASSERT
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        Log.println(priority, "PocketKotlin-$tag", message)
    }
}

private class CrashlyticsTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.ERROR || priority == Log.ASSERT
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        //TODO track to crashlytics
    }
}