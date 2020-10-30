package com.pmarchenko.itdroid.pocketkotlin.network

import android.content.Context
import okhttp3.OkHttpClient

/**
 * @author Pavel Marchenko
 */
interface StethoInjector {

    fun injectInto(context: Context)

    fun injectInto(builder: OkHttpClient.Builder)
}