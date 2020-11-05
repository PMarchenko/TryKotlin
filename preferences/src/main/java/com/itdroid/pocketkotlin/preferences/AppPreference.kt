package com.itdroid.pocketkotlin.preferences

import androidx.lifecycle.LiveData
import kotlin.reflect.KProperty

/**
 * @author itdroid
 */
interface AppPreference<T> {

    fun get(checkWorkingThread: Boolean = true): T

    fun set(value: T)

    fun liveData(): LiveData<T>

    operator fun getValue(thisObj: Any?, property: KProperty<*>): T {
        return get()
    }

    operator fun setValue(thisObj: Any?, property: KProperty<*>, value: T) {
        set(value)
    }
}