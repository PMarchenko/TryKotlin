package com.pmarchenko.itdroid.pocketkotlin

import android.app.Application
import com.pmarchenko.itdroid.pocketkotlin.domain.debug.injectStetho

/**
 * @author Pavel Marchenko
 */
@Suppress("unused")
class PocketKotlinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            injectStetho(this)
        }
    }
}