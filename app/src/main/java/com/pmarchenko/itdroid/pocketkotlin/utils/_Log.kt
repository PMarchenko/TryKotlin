package com.pmarchenko.itdroid.pocketkotlin.utils

import android.util.Log
import com.pmarchenko.itdroid.pocketkotlin.BuildConfig

const val LOG_TAG: String = "PocketKotlin"

fun logv(tag: String = LOG_TAG, msg: String, e: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        if (e == null) {
            Log.v(tag, msg)
        } else {
            Log.v(tag, msg, e)
        }
    }
}

fun logi(tag: String = LOG_TAG, msg: String, e: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        if (e == null) {
            Log.i(tag, msg)
        } else {
            Log.i(tag, msg, e)
        }
    }
}

fun logd(tag: String = LOG_TAG, msg: String, e: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        if (e == null) {
            Log.d(tag, msg)
        } else {
            Log.d(tag, msg, e)
        }
    }
}

fun logw(tag: String = LOG_TAG, msg: String, e: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        if (e == null) {
            Log.w(tag, msg)
        } else {
            Log.w(tag, msg, e)
        }
    }
}

fun loge(tag: String = LOG_TAG, msg: String, e: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        if (e == null) {
            Log.e(tag, msg)
        } else {
            Log.e(tag, msg, e)
        }
    }
}

fun logWtf(tag: String = LOG_TAG, msg: String, e: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        if (e == null) {
            Log.wtf(tag, msg)
        } else {
            Log.wtf(tag, msg, e)
        }
    }
}


inline fun <T> T.measure(tag: String = "$LOG_TAG:Measure", block: T.() -> Unit) {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    Log.e(tag, "block execution took ${(end - start) / 1e6} ms")
}