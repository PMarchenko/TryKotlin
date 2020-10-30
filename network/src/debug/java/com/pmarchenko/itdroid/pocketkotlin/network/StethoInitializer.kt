package com.pmarchenko.itdroid.pocketkotlin.network

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.pmarchenko.itdroid.pocketkotlin.utils.DebugToolsInitializer

/**
 * @author Pavel Marchenko
 */
@Suppress("unused")
@Keep
class StethoInitializer : Initializer<Boolean> {

    override fun create(context: Context): Boolean {
        StethoInjectorImpl.injectInto(context)
        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DebugToolsInitializer::class.java)
    }
}