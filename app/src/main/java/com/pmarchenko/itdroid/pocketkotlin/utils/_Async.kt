package com.pmarchenko.itdroid.pocketkotlin.utils

import kotlin.concurrent.thread

/**
 * @author Pavel Marchenko
 */
inline fun async(crossinline operation: () -> Unit) {
    //todo use coroutines
    thread {
        operation()
    }
}