package com.itdroid.pocketkotlin.preferences

/**
 * @author itdroid
 */
internal interface Filter<T> {

    fun apply(data: T): T
}
