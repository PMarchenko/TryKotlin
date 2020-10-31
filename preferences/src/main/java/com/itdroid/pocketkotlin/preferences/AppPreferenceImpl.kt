package com.itdroid.pocketkotlin.preferences

import androidx.lifecycle.LiveData
import com.itdroid.pocketkotlin.utils.checkWorkerThread

/**
 * @author itdroid
 */
internal class AppPreferenceImpl<T>(
    private val key: String,
    private val opt: T,
    private val storage: Storage,
    private val converter: Converter<T>? = null,
    private val filter: Filter<T>? = null,
) : AppPreference<T> {

    override fun get(checkWorkingThread: Boolean): T {
        if (checkWorkingThread) checkWorkerThread()

        @Suppress("UNCHECKED_CAST")
        val raw = storage.read(key, converter?.serialize(opt) ?: opt) as T

        val converted = if (converter != null && raw is String) {
            converter.deserialize(raw, opt)
        } else {
            raw
        }

        return filter?.apply(converted) ?: converted
    }

    override fun set(value: T) {
        if (value != get()) {
            val filtered = filter?.apply(value) ?: value
            val serialized = converter?.serialize(filtered) ?: filtered
            storage.save(key, serialized)
        }
    }

    override fun liveData(): LiveData<T> {
        return storage.asLiveData(key, this)
    }
}