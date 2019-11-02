package com.pmarchenko.itdroid.pocketkotlin

import android.app.Application

/**
 * @author Pavel Marchenko
 */
@Suppress("unused")
class PocketKotlinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            com.facebook.stetho.Stetho.initializeWithDefaults(this)
        }
    }
}