package com.itdroid.pocketkotlin.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

/**
 * @author itdroid
 */
object StethoInjectorImpl : StethoInjector {

    override fun injectInto(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
    }
}
