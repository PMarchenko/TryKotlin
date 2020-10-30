package com.pmarchenko.itdroid.pocketkotlin.preferences

/**
 * @author Pavel Marchenko
 */
internal interface Filter<T> {

    fun apply(data: T): T
}
