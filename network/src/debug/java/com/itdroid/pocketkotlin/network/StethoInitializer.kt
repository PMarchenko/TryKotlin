package com.itdroid.pocketkotlin.network

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.facebook.stetho.Stetho
import com.itdroid.pocketkotlin.utils.DebugToolsInitializer

/**
 * @author itdroid
 */
@Suppress("unused")
@Keep
class StethoInitializer : Initializer<Boolean> {

    override fun create(context: Context): Boolean {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(context.applicationContext)
        }
        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DebugToolsInitializer::class.java)
    }
}
