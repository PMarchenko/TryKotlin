package com.pmarchenko.itdroid.pocketkotlin.network

import android.content.Context
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