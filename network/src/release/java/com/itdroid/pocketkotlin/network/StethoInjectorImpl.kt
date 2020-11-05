package com.itdroid.pocketkotlin.network

import android.content.Context
import okhttp3.OkHttpClient

/**
 * @author itdroid
 */
object StethoInjectorImpl : StethoInjector {

    override fun injectInto(builder: OkHttpClient.Builder) {
        //nothing
    }
}