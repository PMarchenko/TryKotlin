package com.pmarchenko.itdroid.pocketkotlin.domain.utils

/**
 * @author Pavel Marchenko
 */
class ConsumableValue<out T>(private val data: T, private val opt: T? = null) {

    private var isConsumed = false

    fun get(): T? =
        if (isConsumed) {
            opt
        } else {
            isConsumed = true
            data
        }
}