package com.pmarchenko.itdroid.pocketkotlin.utils

/**
 * @author Pavel Marchenko
 */
class LiveDataHolder<out T>(private val data: T, private val opt: T? = null) {

    var isConsumed = false
        private set

    fun get(): T? =
        if (isConsumed) {
            opt
        } else {
            isConsumed = true
            data
        }
}