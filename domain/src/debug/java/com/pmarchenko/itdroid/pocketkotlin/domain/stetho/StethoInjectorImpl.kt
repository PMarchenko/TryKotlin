package com.pmarchenko.itdroid.pocketkotlin.domain.stetho

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pmarchenko.itdroid.pocketkotlin.domain.stetho.StethoInjector
import okhttp3.OkHttpClient

/**
 * @author Pavel Marchenko
 */
object StethoInjectorImpl : StethoInjector {

    override fun injectInto(context: Context) {
        Stetho.initializeWithDefaults(context.applicationContext)
    }

    override fun injectInto(builder: OkHttpClient.Builder) {
        builder.addInterceptor(StethoInterceptor())
    }
}