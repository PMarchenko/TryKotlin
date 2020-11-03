@file:Suppress("unused")
@file:JvmName("PocketKotlinLogger")

package com.itdroid.pocketkotlin.utils

import timber.log.Timber
import kotlin.system.measureNanoTime

inline fun vLog(
    tag: String? = null,
    throwable: Throwable? = null,
    lazyMsg: () -> String? = { null },
) {
    if (tag != null) Timber.tag(tag)
    Timber.v(throwable, lazyMsg())
}

inline fun iLog(
    tag: String? = null,
    throwable: Throwable? = null,
    lazyMsg: () -> String? = { null },
) {
    iLog(tag, throwable, lazyMsg.invoke())
}

fun iLog(
    tag: String? = null,
    throwable: Throwable? = null,
    msg: String?,
) {
    if (tag != null) Timber.tag(tag)
    Timber.i(throwable, msg)
}

inline fun dLog(
    tag: String? = null,
    throwable: Throwable? = null,
    lazyMsg: () -> String? = { null },
) {
    if (tag != null) Timber.tag(tag)
    Timber.d(throwable, lazyMsg())
}

inline fun wLog(
    throwable: Throwable? = null,
    tag: String? = null,
    lazyMsg: () -> String? = { null },
) {
    if (tag != null) Timber.tag(tag)
    Timber.w(throwable, lazyMsg())
}

inline fun eLog(
    throwable: Throwable? = null,
    tag: String? = null,
    lazyMsg: () -> String? = { null },
) {
    if (tag != null) Timber.tag(tag)
    Timber.e(throwable, lazyMsg())
}

inline fun wtfLog(
    tag: String? = null,
    throwable: Throwable? = null,
    lazyMsg: () -> String,
) {
    if (tag != null) Timber.tag(tag)
    Timber.wtf(throwable, lazyMsg())
}

inline fun <T> measureExecutionTime(tag: String? = null, msg: String = "", block: () -> T): T {
    var result: T
    val time = measureNanoTime {
        result = block()
    }
    dLog(tag) { "MEASURE: '$msg' execution took ${time / 1e6} ms" }
    return result
}
