package com.pmarchenko.itdroid.pocketkotlin

import android.app.Application
import com.pmarchenko.itdroid.pocketkotlin.domain.stetho.StethoInjectorImpl

/**
 * @author Pavel Marchenko
 */
@Suppress("unused")
class PocketKotlinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        StethoInjectorImpl.injectInto(this)
    }
}