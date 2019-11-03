package com.pmarchenko.itdroid.pocketkotlin.domain.stetho

import android.content.Context
import com.pmarchenko.itdroid.pocketkotlin.domain.stetho.StethoInjector
import okhttp3.OkHttpClient

/**
 * @author Pavel Marchenko
 */
object StethoInjectorImpl : StethoInjector {

    override fun injectInto(context: Context) {
        //nothing
    }

    override fun injectInto(builder: OkHttpClient.Builder) {
        //nothing
    }
}