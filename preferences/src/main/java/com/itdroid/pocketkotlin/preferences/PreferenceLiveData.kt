package com.itdroid.pocketkotlin.preferences

import androidx.lifecycle.MutableLiveData

/**
 * @author Pavel Marchenko
 */
internal class PreferenceLiveData<T> : MutableLiveData<T>() {

    override fun setValue(value: T) {
        if (value != getValue()) super.setValue(value)
    }

    override fun postValue(value: T) {
        if (value != getValue()) super.postValue(value)
    }
}
