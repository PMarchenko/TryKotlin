package com.itdroid.pocketkotlin.network

import okhttp3.OkHttpClient

/**
 * @author itdroid
 */
interface StethoInjector {

    fun injectInto(builder: OkHttpClient.Builder)
}