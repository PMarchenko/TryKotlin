package com.pmarchenko.itdroid.pocketkotlin.domain.debug

import android.content.Context

/**
 * @author Pavel Marchenko
 */
fun injectStetho(context: Context) {
    com.facebook.stetho.Stetho.initializeWithDefaults(context.applicationContext)
}