package com.itdroid.pocketkotlin.utils

import android.os.Looper

/**
 * @author itdroid
 */

/**
 * Hack to be sure that when statement handled all possible results.
 * If result of when expression is used compiler does this by its own.
 * If result of when expression is not used compiler does NOT check all cases.
 * For example
 *
 * enum class State { A, B, C}
 *
 * Compile time error:
 * val data: Any = when(someValue) {
 *      A -> "One"
 *      B -> "Two"
 * }
 * No error:
 * when(someValue) {
 *      A -> println("Hello")
 *      B -> println("Bye")
 * }
 * but compile time error:
 * when(someValue) {
 *      A -> println("Hello")
 *      B -> println("Bye")
 * }.checkAllMatched
 * */
val <T> T.checkAllMatched: T
    get() = this

val safeCheckUiThread: Boolean
    get() = Thread.currentThread() == Looper.getMainLooper().thread

val safeCheckWorkerThread: Boolean
    get() = !safeCheckUiThread

@Suppress("unused")
fun checkUiThread() {
    if (safeCheckWorkerThread) error("Long running operation in main thread")
}

fun checkWorkerThread() {
    if (safeCheckUiThread) error("Long running operation in main thread")
}

/**
 * Returns `this` value if it satisfies the given [predicate] or [opt], if it doesn't.
 */
fun <T> T.optIfNot(opt: T, predicate: (T) -> Boolean): T {
    return if (predicate(this)) this else opt
}

fun Float.applyRange(min: Float, max: Float): Float =
    when {
        this < min -> min
        this > max -> max
        else -> this
    }
