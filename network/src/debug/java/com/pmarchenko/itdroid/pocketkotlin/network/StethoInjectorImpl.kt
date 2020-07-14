package com.pmarchenko.itdroid.pocketkotlin.network

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

/**
 * @author Pavel Marchenko
 */
object StethoInjectorImpl : StethoInjector {

    override fun injectInto(context: Context) {
        Stetho.initializeWithDefaults(context.applicationContext)
    }

    override fun injectInto(builder: OkHttpClient.Builder) {
        builder.addNetworkInterceptor(StethoInterceptor())
    }
}