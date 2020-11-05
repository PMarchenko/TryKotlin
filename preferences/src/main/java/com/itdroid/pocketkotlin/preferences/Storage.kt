package com.itdroid.pocketkotlin.preferences

import androidx.lifecycle.LiveData

/**
 * @author itdroid
 */
internal interface Storage {

    fun <T> read(key: String, opt: T): T

    fun <T> save(key: String, value: T)

    fun <T> asLiveData(key: String, pref: AppPreferenceImpl<T>): LiveData<T>

    fun <T> notifySaveValue(key: String, value: T)

    fun release()

}